package com.devmmurray.marvel.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmmurray.marvel.data.model.RetrofitFlags
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.data.model.domain.ComicStub
import com.devmmurray.marvel.data.model.domain.SeriesStub
import com.devmmurray.marvel.data.model.dto.CharacterResults
import com.devmmurray.marvel.data.repository.MarvelApiRepo
import kotlinx.coroutines.launch

const val TAG = "Base_View_Model"

open class HomeViewModel() : ViewModel() {

    private val _allCharacters by lazy { MutableLiveData<MutableList<CharacterResults>>() }
    val allCharacters: LiveData<MutableList<CharacterResults>> get() = _allCharacters

    private val _characterList by lazy { MutableLiveData<ArrayList<CharacterObject>>() }
    val characterList: LiveData<ArrayList<CharacterObject>> get() = _characterList

    private val _character by lazy { MutableLiveData<CharacterObject>() }
    val character: LiveData<CharacterObject> get() = _character

    private val _exceptionWatcher by lazy { MutableLiveData<Boolean>() }
    val exceptionWatcher: LiveData<Boolean> get() = _exceptionWatcher


    fun getAllCharacters() {
        val tempList = mutableListOf<CharacterResults>()
        viewModelScope.launch {
            val limit = 1500
            var offset = 0
            while (offset<limit) {
                val result = MarvelApiRepo.get100MarvelCharacters(offset)
                result.body()?.data?.results?.forEach {
                    tempList.add(it)
                    Log.d("Character: ", it.name?:"")
                }
                offset+=100
            }
            _allCharacters.value = tempList
            Log.d("CharacterListSize: ",allCharacters.value?.size?.toString()?:" Empty")
        }
    }


    fun refresh(id: String, flag: RetrofitFlags) {
        loadCharacterData(id, flag)
    }

    fun loadCharacterArray(list: ArrayList<String>, flag: RetrofitFlags) {
        Log.d(TAG, "* * * * * Load Character Array Called * * * * * ")
        list.forEach {
            loadCharacterData(it, flag)
        }
    }


    // Function to call Marvel and return one character
    private fun loadCharacterData(id: String, flag: RetrofitFlags) {
        Log.d(TAG, "***** Load Character Called *****")
        val loadCharacterList = ArrayList<CharacterObject>()
        viewModelScope.launch {
            try {
                val result = MarvelApiRepo.getMarvelCharacter(id)
                if (result.isSuccessful) {
                    result.body()?.data?.results?.forEach {
                        // Temp list to hold the comic stubs received from Marvel
                        val comicsList = ArrayList<ComicStub?>()
                        // Iterating and storing that list Comic Stubs
                        it.comics?.items?.forEach { comicItems ->
                            comicItems.comicId?.let { id ->
                                val stub = loadCharacterComics(id)
                                comicsList.add(stub)
                            }
                        }

                        // Temp list to hold the series stubs received from Marvel
                        val seriesList = ArrayList<SeriesStub?>()
                        // Iterating and storing that list of links
                        it.series?.items?.forEach { seriesItems ->
                            seriesItems.seriesId?.let { id ->
                                val stub = loadCharacterSeries(id)
                                seriesList.add(stub)
                            }
                        }
                        Log.d(TAG, "****** Before Creating Character = ${it.name} ******")
                        val character = CharacterObject(
                            id = it.id,
                            name = it.name,
                            description = it.description,
                            thumbnailLink = it.thumbnail?.thumbnail,
                            posterImage = it.thumbnail?.poster,
                            comics = comicsList,
                            series = seriesList
                        )
                        Log.d(TAG, "****** Character Name = ${character.name} ********")
                        if (flag == RetrofitFlags.SINGLE_CHARACTER) {
                            _character.value = character
                        } else {
                            Log.d(TAG, "* * * * * Adding To Character List * * * * * * ")
                            loadCharacterList.add(character)
                        }
                        _exceptionWatcher.value = false
                    }
                } else {
                    _exceptionWatcher.value = true
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "******** ${e.message} ******")
                _exceptionWatcher.value = true
            }
            _characterList.value = loadCharacterList
        }
    }

    private fun loadCharacterComics(id: String): ComicStub? {
        var comic: ComicStub? = null
        viewModelScope.launch {
            try {
                val result = MarvelApiRepo.getMarvelComic(id)
                if (result.isSuccessful) {
                    result.body()?.data?.results?.forEach { comicResult ->
                        comic = ComicStub(
                            id = comicResult.id,
                            title = comicResult.title,
                            thumbnail = comicResult.thumbnail?.thumbnail
                        )
                    }
                    _exceptionWatcher.value = false
                } else {




                }
            } catch (e: Exception) {



            }
        }
        return comic
    }

    private fun loadCharacterSeries(id: String): SeriesStub? {
        var series: SeriesStub? = null
        viewModelScope.launch {
            val result = MarvelApiRepo.getMarvelSeries(id)
            if (result.isSuccessful) {
                result.body()?.data?.results?.forEach { seriesResults ->
                    series = SeriesStub(
                        id = seriesResults.id,
                        title = seriesResults.title,
                        thumbnail = seriesResults.thumbnail?.thumbnail
                    )

                    _exceptionWatcher.value = false
                }
            } else {

                _exceptionWatcher.value = true
            }
        }
        return series
    }
}
