package com.devmmurray.marvel.ui.viewmodel

import android.app.Application
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
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.util.CharacterRecyclerFlags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel(application: Application) : MainActivityViewModel(application) {

    // Functions to Retrieve Poster Characters
    fun getPosterCharacter(flags: CharacterRecyclerFlags) {
        // List of all character lists
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

        getPosterCharacter(character, flags)
    }

    private fun getPosterCharacter(id: Int, flags: CharacterRecyclerFlags) =
        viewModelScope.launch(Dispatchers.IO) {
            when (flags) {
                CharacterRecyclerFlags.FIRST_POSTER ->
                    _firstPosterLD.postValue(repository.getCharacterByMarvelId(id))
                CharacterRecyclerFlags.SECOND_POSTER ->
                    _secondPosterLD.postValue(repository.getCharacterByMarvelId(id))
                else ->
                    _thirdPosterLD.postValue(repository.getCharacterByMarvelId(id))
            }

        }


    // Functions to retrieve character lists for recyclers
    fun loadList(list: Map<String, Int>, flag: CharacterRecyclerFlags) {
        getList(list, flag)
    }

    private fun getList(list: Map<String, Int>, flag: CharacterRecyclerFlags) {
        val tempList = ArrayList<CharacterObject>()
        viewModelScope.launch {
            list.forEach { (_, id) ->
                tempList.add(repository.getCharacterByMarvelId(id))

            }
        }
        when (flag) {
            CharacterRecyclerFlags.POPULAR -> _popularListLD.value = tempList
            CharacterRecyclerFlags.FEMALE -> _femaleListLD.value = tempList
            CharacterRecyclerFlags.VILLAIN -> _villainListLD.value = tempList
            CharacterRecyclerFlags.AVENGERS -> _avengerListLD.value = tempList
            CharacterRecyclerFlags.SPIDERMAN -> _spidermanListLD.value = tempList
            CharacterRecyclerFlags.XMEN -> _xmenListLD.value = tempList
            CharacterRecyclerFlags.CLASSICS -> _classicsListLD.value = tempList
            CharacterRecyclerFlags.TV -> _tvListLD.value = tempList
            CharacterRecyclerFlags.PUNISHER -> _punisherListLD.value = tempList
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