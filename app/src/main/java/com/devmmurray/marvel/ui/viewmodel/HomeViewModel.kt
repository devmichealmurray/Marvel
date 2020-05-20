package com.devmmurray.marvel.ui.viewmodel

import android.app.Application

open class HomeViewModel(application: Application) : MainActivityViewModel(application) {




//
//    fun refresh(id: String, flag: RetrofitFlags) {
//        loadCharacterData(id, flag)
//    }
//
//    fun loadCharacterArray(list: ArrayList<String>, flag: RetrofitFlags) {
//        Log.d(TAG, "* * * * * Load Character Array Called * * * * * ")
//        list.forEach {
//            loadCharacterData(it, flag)
//        }
//    }
//

    // Function to call Marvel and return one character
//    private fun loadCharacterData(id: String, flag: RetrofitFlags) {
//        Log.d(TAG, "***** Load Character Called *****")
//        val loadCharacterList = ArrayList<CharacterObject>()
//        viewModelScope.launch {
//            try {
//                val result = MarvelApiRepo.getMarvelCharacter(id)
//                if (result.isSuccessful) {
//                    result.body()?.data?.results?.forEach {
//                        // Temp list to hold the comic stubs received from Marvel
//                        val comicsList = ArrayList<String?>()
//                        // Iterating and storing that list Comic Stubs
//                        it.comics?.items?.forEach { comicItems ->
//                            comicItems.comicId?.let { id ->
//                                val stub = loadCharacterComics(id)
//                                comicsList.add(stub)
//                            }
//                        }
//
//                        // Temp list to hold the series stubs received from Marvel
//                        val seriesList = ArrayList<String?>()
//                        // Iterating and storing that list of links
//                        it.series?.items?.forEach { seriesItems ->
//                            seriesItems.seriesId?.let { id ->
//                                val stub = loadCharacterSeries(id)
//                                seriesList.add(stub)
//                            }
//                        }
//                        Log.d(TAG, "****** Before Creating Character = ${it.name} ******")
//                        val character = CharacterObject(
//                            marvelId = it.id,
//                            name = it.name,
//                            description = it.description,
//                            thumbnailLink = it.thumbnail?.thumbnail,
//                            posterImage = it.thumbnail?.poster,
//                            comics = comicsList,
//                            series = seriesList
//                        )
//                        Log.d(TAG, "****** Character Name = ${character.name} ********")
//                        if (flag == RetrofitFlags.SINGLE_CHARACTER) {
//                            _character.value = character
//                        } else {
//                            Log.d(TAG, "* * * * * Adding To Character List * * * * * * ")
//                            loadCharacterList.add(character)
//                        }
//                        _exceptionWatcher.value = false
//                    }
//                } else {
//                    _exceptionWatcher.value = true
//                }
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.e(TAG, "******** ${e.message} ******")
//                _exceptionWatcher.value = true
//            }
//            _characterList.value = loadCharacterList
//        }
//    }
//
//    private fun loadCharacterComics(id: String): ComicStub? {
//        var comic: ComicStub? = null
//        viewModelScope.launch {
//            try {
//                val result = MarvelApiRepo.getMarvelComic(id)
//                if (result.isSuccessful) {
//                    result.body()?.data?.results?.forEach { comicResult ->
//                        comic = ComicStub(
//                            id = comicResult.id,
//                            title = comicResult.title,
//                            thumbnail = comicResult.thumbnail?.thumbnail
//                        )
//                    }
//                    _exceptionWatcher.value = false
//                } else {
//
//
//
//
//                }
//            } catch (e: Exception) {
//
//
//
//            }
//        }
//        return comic
//    }
//
//    private fun loadCharacterSeries(id: String): SeriesStub? {
//        var series: SeriesStub? = null
//        viewModelScope.launch {
//            val result = MarvelApiRepo.getMarvelSeries(id)
//            if (result.isSuccessful) {
//                result.body()?.data?.results?.forEach { seriesResults ->
//                    series = SeriesStub(
//                        id = seriesResults.id,
//                        title = seriesResults.title,
//                        thumbnail = seriesResults.thumbnail?.thumbnail
//                    )
//
//                    _exceptionWatcher.value = false
//                }
//            } else {
//
//                _exceptionWatcher.value = true
//            }
//        }
//        return series
//    }
}
