package com.devmmurray.marvel.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.marvel.data.Lists.Companion.avengersMap
import com.devmmurray.marvel.data.Lists.Companion.classicsMap
import com.devmmurray.marvel.data.Lists.Companion.femaleCharacterArray
import com.devmmurray.marvel.data.Lists.Companion.popularCharacterArray
import com.devmmurray.marvel.data.Lists.Companion.punisherMap
import com.devmmurray.marvel.data.Lists.Companion.spidermanMap
import com.devmmurray.marvel.data.Lists.Companion.topVillainsArray
import com.devmmurray.marvel.data.Lists.Companion.tvShowCharacters
import com.devmmurray.marvel.data.Lists.Companion.xmenMap
import com.devmmurray.marvel.data.model.UrlAddress
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.data.model.entities.CharacterComicsEntity
import com.devmmurray.marvel.data.model.entities.CharacterEntity
import com.devmmurray.marvel.data.model.entities.CharacterSeriesEntity
import com.devmmurray.marvel.data.repository.MarvelApiRepo
import com.devmmurray.marvel.util.CharacterFlags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "CharactersViewModel"

class CharactersViewModel(application: Application) : MainActivityViewModel(application) {

    // Functions to Retrieve Poster Characters
    fun getPosterCharacter(flags: CharacterFlags) {
        // List of all character lists
        Log.d(TAG, "************ getPosterCharacter Called ****************")
        val listOfMaps = arrayListOf<Map<String, Int>>(
            popularCharacterArray,
            femaleCharacterArray,
            topVillainsArray,
            avengersMap,
            spidermanMap,
            xmenMap,
            classicsMap,
            tvShowCharacters,
            punisherMap
        )
        // Randomly choose a list
        val listPosition = (0 until listOfMaps.size - 1).random()
        val map = listOfMaps[listPosition]

        // Move all IDs to an array
        val idArray = ArrayList<Int>()
        for ((k, v) in map) {
            idArray.add(v)
        }
        // Randomly choose an Id
        val mapPosition = (0 until map.size - 1).random()
        val character = idArray[mapPosition]

        posterCharacter(character, flags)
    }

    private fun posterCharacter(id: Int, flags: CharacterFlags) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "************ posterCharacter Called: ID = $id ****************")
            val checkId = repository.checkMarvelId(id)
            if (checkId == null) {
                getMarvelCharacter(id)
            } else {
                when (flags) {
                    CharacterFlags.FIRST_POSTER ->
                        _firstPosterLD.postValue(repository.getCharacterByMarvelId(id))
                    CharacterFlags.SECOND_POSTER ->
                        _secondPosterLD.postValue(repository.getCharacterByMarvelId(id))
                    else ->
                        _thirdPosterLD.postValue(repository.getCharacterByMarvelId(id))
                }
            }
        }


    // Functions to retrieve character lists for recyclers
    fun loadList(list: Map<String, Int>, flag: CharacterFlags) {
        Log.d(TAG, "************ loadList Called ****************")
        getList(list, flag)
    }

    private fun getList(list: Map<String, Int>, flag: CharacterFlags) {
        val tempList = ArrayList<CharacterObject>()
        Log.d(TAG, "************ getList Called ****************")
        viewModelScope.launch {
            list.forEach { (_, id) ->
                Log.d(TAG, "********** get list adding character id = $id ***********")
                val checkId = repository.checkMarvelId(id)
                if (checkId != null) {
                    tempList.add(repository.getCharacterByMarvelId(id))
                } else {
                    getMarvelCharacter(id)
                    Log.d(TAG, "*** .loadList character id = $id is null***********")
                }
            }
        }

        when (flag) {
            CharacterFlags.POPULAR -> _popularListLD.value = tempList
            CharacterFlags.FEMALE -> _femaleListLD.value = tempList
            CharacterFlags.VILLAIN -> _villainListLD.value = tempList
            CharacterFlags.AVENGERS -> _avengerListLD.value = tempList
            CharacterFlags.SPIDERMAN -> _spidermanListLD.value = tempList
            CharacterFlags.XMEN -> _xmenListLD.value = tempList
            CharacterFlags.CLASSICS -> _classicsListLD.value = tempList
            CharacterFlags.TV -> _tvListLD.value = tempList
            CharacterFlags.PUNISHER -> _punisherListLD.value = tempList
        }
    }

    private fun getMarvelCharacter(id: Int) {
        Log.d(TAG, "********* Get Marvel Character Called: ID = $id *****************")
        viewModelScope.launch {
            val result = MarvelApiRepo.getMarvelCharacter(id.toString())
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
                    timeStamp = UrlAddress.CURRENT_TIME,
                    name = it.name,
                    description = it.description,
                    thumbnail = it.thumbnail?.thumbnail,
                    poster = it.thumbnail?.poster
                )
                Log.d(TAG, "* * * * *  adding character ${character.name} * * * * * ")
                addCharacter(character)
            }
        }
    }


    /**
     *  Live Data Variables
     */


    // Large Poster Live Data
    private val _firstPosterLD by lazy { MutableLiveData<CharacterObject>() }
    val firstPosterLD: LiveData<CharacterObject> get() = _firstPosterLD

    private val _secondPosterLD by lazy { MutableLiveData<CharacterObject>() }
    val secondPosterLD: LiveData<CharacterObject> get() = _secondPosterLD

    private val _thirdPosterLD by lazy { MutableLiveData<CharacterObject>() }
    val thirdPosterLD: LiveData<CharacterObject> get() = _thirdPosterLD


    // List Recycler Live Data
    private val _popularListLD by lazy { MutableLiveData<ArrayList<CharacterObject>>() }
    val popularListLD: LiveData<ArrayList<CharacterObject>> get() = _popularListLD

    private val _femaleListLD by lazy { MutableLiveData<ArrayList<CharacterObject>>() }
    val femaleListLD: LiveData<ArrayList<CharacterObject>> get() = _femaleListLD

    private val _villainListLD by lazy { MutableLiveData<ArrayList<CharacterObject>>() }
    val villainListLD: LiveData<ArrayList<CharacterObject>> get() = _villainListLD

    private val _avengerListLD by lazy { MutableLiveData<ArrayList<CharacterObject>>() }
    val avengerListLD: LiveData<ArrayList<CharacterObject>> get() = _avengerListLD

    private val _spidermanListLD by lazy { MutableLiveData<ArrayList<CharacterObject>>() }
    val spidermanListLD: LiveData<ArrayList<CharacterObject>> get() = _spidermanListLD

    private val _xmenListLD by lazy { MutableLiveData<ArrayList<CharacterObject>>() }
    val xmenListLD: LiveData<ArrayList<CharacterObject>> get() = _xmenListLD

    private val _classicsListLD by lazy { MutableLiveData<ArrayList<CharacterObject>>() }
    val classicsListLD: LiveData<ArrayList<CharacterObject>> get() = _classicsListLD

    private val _tvListLD by lazy { MutableLiveData<ArrayList<CharacterObject>>() }
    val tvListLD: LiveData<ArrayList<CharacterObject>> get() = _tvListLD

    private val _punisherListLD by lazy { MutableLiveData<ArrayList<CharacterObject>>() }
    val punisherListLD: LiveData<ArrayList<CharacterObject>> get() = _punisherListLD

}