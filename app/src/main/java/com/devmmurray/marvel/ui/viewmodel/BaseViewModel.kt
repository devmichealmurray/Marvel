package com.devmmurray.marvel.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.data.model.domain.ComicStub
import com.devmmurray.marvel.data.model.domain.SeriesStub
import com.devmmurray.marvel.data.repository.MarvelApiRepo
import kotlinx.coroutines.launch

const val TAG = "Base_View_Model"

open class BaseViewModel() : ViewModel() {

    private val _character by lazy { MutableLiveData<CharacterObject>() }
    val character: LiveData<CharacterObject> get() = _character

    private val _exceptionWatcher by lazy { MutableLiveData<Boolean>() }
    val exceptionWatcher: LiveData<Boolean> get() = _exceptionWatcher


    fun refresh(id: String) {
        loadCharacterData(id)
    }


    // Function to call Marvel and return one character
    private fun loadCharacterData(id: String) {
        Log.d(TAG, "***** Load Character Called *****")
        viewModelScope.launch {
            try {
                val result = MarvelApiRepo.getMarvelCharacter(id)
                if (result.isSuccessful) {
                    result.body()?.data?.results?.forEach {
                        // Temp list to hold the comic links received from Marvel
                        val comicUrlList = ArrayList<String>()
                        // Iterating and storing that list of links
                        it.comics?.items?.forEach { comicItems ->
                            comicItems.comicUrl?.let { link -> comicUrlList.add(link) }
                        }
                        val comicsList = loadCharacterComics(comicUrlList)

                        // Temp list to hold the series links received from Marvel
                        val seriesUrlList = ArrayList<String>()
                        // Iterating and storing that list of links
                        it.series?.items?.forEach { seriesItems ->
                            seriesItems.seriesUrl?.let { link -> seriesUrlList.add(link) }
                        }
                        val seriesList = loadCharacterSeries(seriesUrlList)

                        val character = CharacterObject(
                            id = it.id,
                            name = it.name,
                            description = it.description,
                            thumbnailLink = it.thumbnail?.square,
                            posterImage = it.thumbnail?.poster,
                            comics = comicsList,
                            series = seriesList
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "******** ${e.message} ******")
                _exceptionWatcher.value = true
            }
        }
    }

    private fun loadCharacterComics(list: ArrayList<String>): List<ComicStub> {
        Log.d(TAG, "***** Load Character Comics Called ******")
        val comicList = ArrayList<ComicStub>()
        viewModelScope.launch {
            try {
                list.forEach {
                    val result = MarvelApiRepo.getMarvelComic(it)
                    if (result.isSuccessful) {
                        result.body()?.data?.results?.forEach { comicResult ->
                            val comic = ComicStub(
                                id = comicResult.id,
                                title = comicResult.title,
                                thumbnail = comicResult.thumbnail?.square
                            )
                            comicList.add(comic)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "****** ${e.message} ******")
            }
        }
        return comicList
    }

    private fun loadCharacterSeries(list: ArrayList<String>): List<SeriesStub> {
        Log.d(TAG, "****** Load Character Series Called ******")
        val seriesList = ArrayList<SeriesStub>()
        viewModelScope.launch {
            list.forEach {
                val result = MarvelApiRepo.getMarvelSeries(it)
                if (result.isSuccessful) {
                    result.body()?.data?.results?.forEach { seriesResults ->
                        val series = SeriesStub(
                            id = seriesResults.id,
                            title = seriesResults.title,
                            thumbnail = seriesResults.thumbnail?.square
                        )
                        seriesList.add(series)
                    }
                }
            }
        }
        return seriesList
    }

}