package com.devmmurray.marvel.data.api

import com.devmmurray.marvel.data.model.dto.CharacterDto
import com.devmmurray.marvel.data.model.dto.ComicDto
import com.devmmurray.marvel.data.model.dto.SeriesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/characters/{id}")
    suspend fun getMarvelCharacter(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response<CharacterDto>

    @GET("v1/public/comics/{id}")
    suspend fun getMarvelComic(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response<ComicDto>

    @GET("v1/public/series/{id}")
    suspend fun getMarvelSeries(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response<SeriesDto>
}