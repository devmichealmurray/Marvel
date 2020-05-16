package com.devmmurray.marvel.data.repository

import com.devmmurray.marvel.data.api.MarvelApiService
import com.devmmurray.marvel.data.model.UrlAddress.Companion.API_KEY
import com.devmmurray.marvel.data.model.UrlAddress.Companion.HASH
import com.devmmurray.marvel.data.model.UrlAddress.Companion.TS
import com.devmmurray.marvel.data.model.dto.CharacterDto
import com.devmmurray.marvel.data.model.dto.ComicDto
import com.devmmurray.marvel.data.model.dto.SeriesDto
import retrofit2.Response

object MarvelApiRepo {

    suspend fun get100MarvelCharacters(offset: Int): Response<CharacterDto> {
        return MarvelApiService.apiClient.get100MarvelCharacter(
            offset = offset,
            limit = 100,
            ts = "1",
            apiKey = "2b4c8ae29183275bab5c86e66e208907",
            hash = "7371ae1e35ece11b7ad8af3f806a12b7"
        )
    }

    suspend fun getMarvelCharacter(characterId: String): Response<CharacterDto> {
        return MarvelApiService.apiClient.getMarvelCharacter(
            id = characterId,
            ts = TS,
            apiKey = API_KEY,
            hash = HASH
        )
    }

    suspend fun getMarvelComic(comicId: String): Response<ComicDto> {
        return MarvelApiService.apiClient.getMarvelComic(
            id = comicId,
            ts = TS,
            apiKey = API_KEY,
            hash = HASH
        )
    }

    suspend fun getMarvelSeries(seriesId: String): Response<SeriesDto> {
        return MarvelApiService.apiClient.getMarvelSeries(
            id = seriesId,
            ts = TS,
            apiKey = API_KEY,
            hash = HASH
        )
    }
}