package com.devmmurray.marvel.data.api

import com.devmmurray.marvel.data.model.dto.CharacterDto
import com.devmmurray.marvel.data.model.dto.ComicsDto
import com.devmmurray.marvel.data.model.dto.SeriesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    // Calls to retrieve Characters

    @GET("v1/public/characters")
    suspend fun get100MarvelCharacter(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response<CharacterDto>

    @GET("v1/public/characters/{id}")
    suspend fun getMarvelCharacter(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response<CharacterDto>



    // Calls to retrieve Comics

    @GET("v1/public/comics")
    suspend fun get100MarvelComics(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response<ComicsDto>

    @GET("v1/public/comics/{id}")
    suspend fun getMarvelComic(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response<ComicsDto>



    // Calls to retrieve Series

    @GET("v1/public/series")
    suspend fun get100MarvelSeries(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response<SeriesDto>

    @GET("v1/public/series/{id}")
    suspend fun getMarvelSeries(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response<SeriesDto>
}