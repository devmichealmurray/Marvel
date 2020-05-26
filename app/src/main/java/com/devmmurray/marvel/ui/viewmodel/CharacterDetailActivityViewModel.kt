package com.devmmurray.marvel.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.marvel.data.model.UrlAddress
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.data.model.domain.Comic
import com.devmmurray.marvel.data.model.domain.Series
import com.devmmurray.marvel.data.model.entities.*
import com.devmmurray.marvel.data.repository.MarvelApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "CharDetailViewModel"

class CharacterDetailActivityViewModel(application: Application) :
    MainActivityViewModel(application) {

    private val _returnedCharacter by lazy { MutableLiveData<CharacterObject>() }
    val returnedCharacter: LiveData<CharacterObject> get() = _returnedCharacter

    private val _returnedComics by lazy { MutableLiveData<ArrayList<Comic>>() }
    val returnedComics: LiveData<ArrayList<Comic>> get() = _returnedComics

    private val _returnedSeries by lazy { MutableLiveData<ArrayList<Series>>() }
    val returnedSeries: LiveData<ArrayList<Series>> get() = _returnedSeries

    fun getCharacter(id: Int) {
        getCharacterDetails(id)
        getCharacterComics(id)
        getCharacterSeries(id)
    }

    private fun getCharacterDetails(id: Int) {
        Log.d(TAG, "____________ getCharacterDetails Called Id = $id")
        viewModelScope.launch {
            val character =
                repository.getCharacterByMarvelId(id)
            _returnedCharacter.postValue(character)
        }
    }

    private fun getCharacterComics(id: Int) {
        val tempList = ArrayList<Comic>()
        viewModelScope.launch {
            val characterComicsIds =
                repository.getCharacterComics(id)

            characterComicsIds.forEach {
                val comic = it.comicId?.let { id -> repository.getComicByMarvelId(id) }
                if (comic?.title == null) {
                    val result = MarvelApiRepo.getMarvelComic(it.comicId.toString())
                    result.body()?.data?.results?.forEach { thisComic ->
                        thisComic.dates?.forEach { item ->
                            val date =
                                ComicDateEntity(
                                    comicId = thisComic.marvelId,
                                    type = item.type,
                                    date = item.date
                                )
                            addComicDate(date)
                        }

                        thisComic.characters?.items?.forEach { item ->
                            val character =
                                ComicsCharacterEntity(
                                    comicId = thisComic.marvelId,
                                    characterId = item.characterId
                                )
                            addComicCharacter(character)
                        }

                        val newComic = ComicsEntity(
                            marvelId = thisComic.marvelId,
                            timeStamp = UrlAddress.CURRENT_TIME,
                            title = thisComic.title,
                            issueNumber = thisComic.issueNumber,
                            description = thisComic.description,
                            isbn = thisComic.isbn,
                            pageCount = thisComic.pageCount,
                            series = thisComic.series?.seriesId,
                            thumbnail = thisComic.thumbnail?.thumbnail
                        )
                        Log.d(TAG, "* * * * *  adding comic ${newComic.title} * * * * * ")
                        addComic(newComic)
                        tempList.add(newComic.toComicObject())
                    }
                } else {
                    tempList.add(comic)
                }
            }
        }
        _returnedComics.value = tempList
    }

    private fun addComic(comic: ComicsEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addComic(comic)
    }

    private fun addComicDate(date: ComicDateEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addComicDate(date)
    }

    private fun addComicCharacter(character: ComicsCharacterEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addComicCharacter(character)
        }


    private fun getCharacterSeries(id: Int) {
        val tempList = ArrayList<Series>()
        viewModelScope.launch {
            val characterSeriesIds =
                repository.getCharacterSeries(id)

            characterSeriesIds.forEach {
                val series = it.seriesId?.let { id -> repository.getSeriesByMarvelId(id) }
                if (series?.title == null) {
                    val result = MarvelApiRepo.getMarvelSeries(it.seriesId.toString())
                    result.body()?.data?.results?.forEach { thisSeries ->

                        thisSeries.characters?.seriesCharacter?.forEach { item ->
                            val character =
                                SeriesCharacterEntity(
                                    seriesId = thisSeries.marvelId,
                                    characterId = item.characterId
                                )
                            addSeriesCharacter(character)
                        }

                        thisSeries.comics?.comicItems?.forEach { item ->
                            val comic =
                                SeriesComicEntity(
                                    seriesId = thisSeries.marvelId,
                                    comicId = item.comicId
                                )
                            addSeriesComic(comic)
                        }

                        val newSeries = SeriesEntity(
                            marvelId = thisSeries.marvelId,
                            timeStamp = UrlAddress.CURRENT_TIME,
                            title = thisSeries.title,
                            description = thisSeries.description,
                            startYear = thisSeries.startYear,
                            endYear = thisSeries.endYear,
                            thumbnail = thisSeries.thumbnail?.thumbnail
                        )
                        Log.d(TAG, "* * * * *  adding series ${series?.title} * * * * * ")
                        addSeries(newSeries)
                        tempList.add(newSeries.toSeriesObject())
                    }
                } else {
                    tempList.add(series)
                }

            }
        }
    _returnedSeries.value = tempList
    }

    private fun addSeries(series: SeriesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addSeries(series)
    }

    private fun addSeriesCharacter(character: SeriesCharacterEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSeriesCharacter(character)
        }

    private fun addSeriesComic(comic: SeriesComicEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSeriesComic(comic)


        }
}