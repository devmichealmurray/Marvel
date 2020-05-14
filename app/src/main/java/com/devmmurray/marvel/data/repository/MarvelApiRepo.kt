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