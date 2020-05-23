package com.devmmurray.marvel.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.marvel.data.database.RoomDatabaseClient
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.data.model.entities.CharacterComicsEntity
import com.devmmurray.marvel.data.model.entities.CharacterEntity
import com.devmmurray.marvel.data.model.entities.CharacterSeriesEntity
import com.devmmurray.marvel.data.repository.DatabaseRepository
import com.devmmurray.marvel.data.repository.MarvelApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

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

    // Functions to Check if database has been loaded

    private val _checkCharacterDatabase  by lazy { MutableLiveData<CharacterObject>() }
    val checkCharacterDatabaseLD: LiveData<CharacterObject> get() = _checkCharacterDatabase

    fun checkDatabase() = viewModelScope.launch(Dispatchers.IO) {
        _checkCharacterDatabase.postValue(repository.checkCharacterDatabase())
    }

    // Live Data sends response to confirm the characters DB has finished loading

    private val _characterDataBaseIsLoaded by lazy { MutableLiveData<Boolean>() }
    val characterDataBaseIsLoaded: LiveData<Boolean> get() = _characterDataBaseIsLoaded


    /**
     *  Functions to Load Database with Marvel Json
     */

    private fun addCharacter(character: CharacterEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addCharacter(character)
    }

    private fun addCharacterComic(comic: CharacterComicsEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addCharacterComic(comic)
    }

    private fun addCharacterSeries(series: CharacterSeriesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addCharacterSeries(series)
    }



    fun getAllCharacters() {
        val tempList = mutableListOf<CharacterObject>()
        viewModelScope.launch {
            val limit = 1500
            var offset = 0
            if (offset == limit) {
                _characterDataBaseIsLoaded.value = true
            }
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
    }


}