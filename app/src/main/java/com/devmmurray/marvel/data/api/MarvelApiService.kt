package com.devmmurray.marvel.data.api

import com.devmmurray.marvel.data.model.UrlAddress.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MarvelApiService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val apiClient: MarvelApi = retrofit.create(MarvelApi::class.java)
}