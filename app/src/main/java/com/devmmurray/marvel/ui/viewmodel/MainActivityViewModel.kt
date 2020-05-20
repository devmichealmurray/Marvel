package com.devmmurray.marvel.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.marvel.data.database.CharactersDBClient
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.data.model.entities.CharacterComicsEntity
import com.devmmurray.marvel.data.model.entities.CharacterEntity
import com.devmmurray.marvel.data.model.entities.CharacterSeriesEntity
import com.devmmurray.marvel.data.repository.CharacterDbRepo
import com.devmmurray.marvel.data.repository.MarvelApiRepo
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Set Up Of Character Database
     */
    val repository: CharacterDbRepo

    init {
        val characterDAO = CharactersDBClient.getDbInstance(application).characterDao()
        repository = CharacterDbRepo(characterDAO)
    }

    // Functions to Check if database has been loaded

    private val _checkDatabaseLD  by lazy { MutableLiveData<CharacterObject>() }
    val checkDatabaseLD: LiveData<CharacterObject> get() = _checkDatabaseLD

    fun checkDatabase() = viewModelScope.launch(Dispatchers.IO) {
        _checkDatabaseLD.postValue(repository.checkDatabase())
    }

    // Live Data sends response to confirm the characters DB has finished loading
    private val _dataBaseIsLoaded by lazy { MutableLiveData<Boolean>() }
    val dataBaseIsLoaded: LiveData<Boolean> get() = _dataBaseIsLoaded

//
//    private val _allCharacters by lazy { MutableLiveData<MutableList<CharacterObject>>() }
//    val allCharacters: LiveData<MutableList<CharacterObject>> get() = _allCharacters
//

    /**
     *  Functions to Load Database with Marvel Json
     */

    private fun addCharacter(character: CharacterEntity) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("MainViewModel", "******* Adding Character ${character.name} *********")
        repository.addCharacter(character)
    }

    private fun addCharacterComic(comic: CharacterComicsEntity) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("MainViewModel", "******* Adding Comic ${comic.comicId} *********")
        repository.addCharacterComic(comic)
    }

    private fun addCharacterSeries(series: CharacterSeriesEntity) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("MainViewModel", "******* Adding Series ${series.seriesId} *********")
        repository.addCharacterSeries(series)
    }



    fun getAllCharacters() {
        val tempList = mutableListOf<CharacterObject>()
        viewModelScope.launch {
            val limit = 1500
            var offset = 0
            if (offset == limit) {
                _dataBaseIsLoaded.value = true
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

    /**
     *  Navigation
     */

    val navigateToCharacters = LiveEvent<Boolean>()

    fun navigateToCharactersFragment() {
        navigateToCharacters.value = true
    }

    val navigateToComics = LiveEvent<Boolean>()

    fun navigateToComicsFragment() {
        navigateToComics.value = true
    }

    val navigateToSeries = LiveEvent<Boolean>()

    fun navigateToSeriesFragment() {
        navigateToSeries.value = true
    }
}