package com.devmmurray.marvel.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.marvel.data.database.RoomDatabaseClient
import com.devmmurray.marvel.data.model.UrlAddress.Companion.CURRENT_TIME
import com.devmmurray.marvel.data.model.UrlAddress.Companion.TIME_LAPSE
import com.devmmurray.marvel.data.model.entities.CharacterComicsEntity
import com.devmmurray.marvel.data.model.entities.CharacterEntity
import com.devmmurray.marvel.data.model.entities.CharacterSeriesEntity
import com.devmmurray.marvel.data.repository.DatabaseRepository
import com.devmmurray.marvel.data.repository.MarvelApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainActViewModel"

open class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Set Up Of Database
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

    fun deleteData() {
        viewModelScope.launch {
            repository.deleteAllComics()
            repository.deleteAllComics()
        }
    }

    fun checkForUpdate() {
        viewModelScope.launch {
            val checkDb = repository.checkCharacterDatabase()
            if (checkDb?.timeStamp != null) {
                val timeStamp = checkDb.timeStamp
                if (CURRENT_TIME - timeStamp > TIME_LAPSE) {
                    deleteAllCharacaters()
                    getAllCharacters()
                } else {
                    _charactersUpToDate.postValue(true)
                }
            } else {
                getAllCharacters()
            }
        }
    }

    /**
     *  Live Data Values to update Main Activity when a fragment is ready
     */
    private val _charactersUpToDate by lazy { MutableLiveData<Boolean>() }
    val characterUpToDate: LiveData<Boolean> get() = _charactersUpToDate


    /**
     *  Functions to Load Database with Marvel Json - Nothing Below these functions -
     */
    private fun getAllCharacters() {
        Log.d(TAG, " * * * * *  GetALLCharacters Called * * * * * * ")
        var counter = 1
        viewModelScope.launch {
            val limit = 1500
            var offset = 0

            while (offset < limit) {
                val result = MarvelApiRepo.get100MarvelCharacters(offset)
                result.body()?.data?.results?.forEach {

                    it.comics?.items?.forEach { item ->
                        val comic =
                            CharacterComicsEntity(
                                characterId = it.marvelId,
                                comicId = item.comicId?.toInt()
                            )
                        addCharacterComic(comic)
                    }

                    it.series?.items?.forEach { item ->
                        val series =
                            CharacterSeriesEntity(
                                characterId = it.marvelId,
                                seriesId = item.seriesId?.toInt()
                            )
                        addCharacterSeries(series)
                    }

                    val character = CharacterEntity(
                        marvelId = it.marvelId,
                        timeStamp = CURRENT_TIME,
                        name = it.name,
                        description = it.description,
                        thumbnail = it.thumbnail?.thumbnail,
                        poster = it.thumbnail?.poster
                    )
                    Log.d(TAG, "* * * * *  adding character ${character.name} * * * * * ")
                    addCharacter(character)
                    counter++
                }
                Log.d(TAG, "* * * * * * * * Offset = $offset * * * * * * * ")
                Log.d(TAG, "* * * * * * * *  Counter = $counter * * * * * * * ")
                offset += 100
            }
            _charactersUpToDate.postValue(true)
        }

    }


    fun addCharacter(character: CharacterEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCharacter(character)
        }

    fun addCharacterComic(comic: CharacterComicsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCharacterComic(comic)
        }

    fun addCharacterSeries(series: CharacterSeriesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCharacterSeries(series)
        }

    private fun deleteAllCharacaters() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCharacters()
        }


    fun countCharacters() {
        viewModelScope.launch {
            val count = repository.countCharacterDB()
            Log.d(TAG, "* * * * * * * * * *  Count Database MarvelIds = $count * * * * * * *")
        }
    }
}