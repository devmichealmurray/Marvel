package com.devmmurray.marvel.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.marvel.data.model.domain.CharacterObject
import kotlinx.coroutines.launch

class CharacterDetailActivityViewModel(application: Application): MainActivityViewModel(application) {

    private val _returnedCharacter by lazy { MutableLiveData<CharacterObject>() }
    val returnedCharacter: LiveData<CharacterObject> get() = _returnedCharacter

    fun getCharacter(id: Int) {
        getCharacterDetails(id)
    }

    private fun getCharacterDetails(id: Int) {
        viewModelScope.launch {
            val character =
                repository.getCharacterByMarvelId(id)
            _returnedCharacter.postValue(character)
        }
    }

    private fun getCharacterComics(id: Int) {
        viewModelScope.launch {
            val characterComics =
                repository.getCharacterComics(id)
        }
    }

}