package com.devmmurray.marvel.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.marvel.data.database.RoomDatabaseClient
import com.devmmurray.marvel.data.model.UrlAddress
import com.devmmurray.marvel.data.model.UrlAddress.Companion.CURRENT_TIME
import com.devmmurray.marvel.data.model.UrlAddress.Companion.TIME_LAPSE
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.data.model.domain.Comic
import com.devmmurray.marvel.data.model.domain.Series
import com.devmmurray.marvel.data.model.entities.*
import com.devmmurray.marvel.data.repository.DatabaseRepository
import com.devmmurray.marvel.data.repository.MarvelApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var characterUpToDate = false
    private var comicsUpToDate = false
    private var seriesUpToDate = false

    /**
     * Set Up Of Character Database
     */
    val repository: DatabaseRepository

    init {
        val characterDAO = RoomDatabaseClient.getDbInstance(application).characterDao()
        val comicsDAO = RoomDatabaseClient.getDbInstance(application).comicsDao()
        val seriesDAO = RoomDatabaseClient.getDbInstance(application).seriesDao()
        repository = DatabaseRepository(characterDAO, comicsDAO, seriesDAO)
    }

    /**
     *  Functions To Check and/or update local database
     */

    fun checkDatabase() {
        checkAllDatabaseTables()
    }

    private fun checkAllDatabaseTables() {
        lateinit var characterTable: CharacterObject
        lateinit var comicsTable: Comic
        lateinit var seriesTable: Series

        viewModelScope.launch(Dispatchers.IO) {
            characterTable = repository.checkCharacterDatabase()
            comicsTable = repository.checkComicsDatabase()
            seriesTable = repository.checkSeriesDatabase()
        }

        if (characterTable.timeStamp == null
            || CURRENT_TIME - characterTable.timeStamp!! >= UrlAddress.TIME_LAPSE
        ) {
            getAllCharacters()
        } else {
            characterUpToDate = true
            dataIsLoaded()
        }

        if (comicsTable.timeStamp == null
            || CURRENT_TIME - comicsTable.timeStamp!! >= TIME_LAPSE
        ) {
            getAllComics()
        } else {
            comicsUpToDate = true
            dataIsLoaded()
        }

        if (seriesTable.timeStamp == null
            || CURRENT_TIME - comicsTable.timeStamp!! >= TIME_LAPSE
        ) {
            getAllSeries()
        } else {
            seriesUpToDate = true
            dataIsLoaded()
        }
    }

    /**
     * Function to update live data when db is loaded and ready to use
     */

    private fun dataIsLoaded() {
        if (characterUpToDate && comicsUpToDate && seriesUpToDate) {
            _dataBaseIsLoaded.value = true
        }
    }

    /**
     * Live Data sends response to MainActivity to confirm the DB has finished loading
     */

    private val _dataBaseIsLoaded by lazy { MutableLiveData<Boolean>() }
    val dataBaseIsLoaded: LiveData<Boolean> get() = _dataBaseIsLoaded

    private val _dataLoadingToasts by lazy { MutableLiveData<Int>() }
    val dataLoadingToasts: LiveData<Int> get() = _dataLoadingToasts

    /**
     *  Functions to Load Database with Marvel Json - Nothing Below these functions -
     */
    private fun getAllCharacters() {
        viewModelScope.launch {
            val limit = 1500
            var offset = 0

            while (offset < limit) {
                val result = MarvelApiRepo.get100MarvelCharacters(offset)
                result.body()?.data?.results?.forEach {

                    it.comics?.items?.forEach { item ->
                        val comic =
                            CharacterComicsEntity(
                                characterId = it.id,
                                comicId = item.comicId
                            )
                        addCharacterComic(comic)
                    }

                    it.series?.items?.forEach { item ->
                        val series =
                            CharacterSeriesEntity(
                                characterId = it.id,
                                seriesId = item.seriesId
                            )
                        addCharacterSeries(series)
                    }

                    val character = CharacterEntity(
                        marvelId = it.id,
                        timeStamp = CURRENT_TIME,
                        name = it.name,
                        description = it.description,
                        thumbnail = it.thumbnail?.thumbnail,
                        poster = it.thumbnail?.poster
                    )
                    addCharacter(character)
                }
                offset += 100
            }
        }
        characterUpToDate = true
        dataIsLoaded()
        _dataLoadingToasts.value = 1
    }

    private fun addCharacter(character: CharacterEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addCharacter(character)
    }

    private fun addCharacterComic(comic: CharacterComicsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCharacterComic(comic)
        }

    private fun addCharacterSeries(series: CharacterSeriesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCharacterSeries(series)
        }


    private fun getAllComics() {
        viewModelScope.launch {
            val limit = 47_100
            var offset = 0

            while (offset < limit) {
                val result = MarvelApiRepo.get100MarvelComics(offset)
                result.body()?.data?.results?.forEach { it ->

                    it.dates?.forEach { item ->
                        val date =
                            ComicDateEntity(
                                comicId = it.marvelId,
                                type = item.type,
                                date = item.date
                            )
                        addComicDate(date)
                    }

                    it.characters?.items?.forEach { item ->
                        val character =
                            ComicsCharacterEntity(
                                comicId = it.marvelId,
                                characterId = item.characterId
                            )
                        addComicCharacter(character)
                    }

                    val comic = ComicsEntity(
                        marvelId = it.marvelId,
                        timeStamp = CURRENT_TIME,
                        title = it.title,
                        issueNumber = it.issueNumber,
                        description = it.description,
                        isbn = it.isbn,
                        pageCount = it.pageCount,
                        series = it.series?.seriesId,
                        thumbnail = it.thumbnail?.thumbnail
                    )
                    addComic(comic)
                }
                offset += 100
            }
        }
        comicsUpToDate = true
        dataIsLoaded()
        _dataLoadingToasts.value = 2
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


    private fun getAllSeries() {
        viewModelScope.launch {
            val limit = 11_800
            var offset = 0

            while (offset < limit) {
                val result = MarvelApiRepo.get100MarvelSeries(offset)
                result.body()?.data?.results?.forEach { it ->

                    it.characters?.seriesCharacter?.forEach { item ->
                        val character =
                            SeriesCharacterEntity(
                                seriesId = it.marvelId,
                                characterId = item.characterId
                            )
                        addSeriesCharacter(character)
                    }

                    it.comics?.comicItems?.forEach { item ->
                        val comic =
                            SeriesComicEntity(
                                seriesId = it.marvelId,
                                comicId = item.comicId
                            )
                        addSeriesComic(comic)
                    }

                    val series = SeriesEntity(
                        marvelId = it.marvelId,
                        timeStamp = CURRENT_TIME,
                        title = it.title,
                        description = it.description,
                        startYear = it.startYear,
                        endYear = it.endYear,
                        thumbnail = it.thumbnail?.thumbnail
                    )
                    addSeries(series)
                }
                offset += 100
            }
        }
        seriesUpToDate = true
        dataIsLoaded()
        _dataLoadingToasts.value = 3
    }

    private fun addSeries(series: SeriesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addSeries(series)
    }

    private fun addSeriesCharacter(character: SeriesCharacterEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSeriesCharacter(character)
        }

    private fun addSeriesComic(comic: SeriesComicEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addSeriesComic(comic)
    }

}