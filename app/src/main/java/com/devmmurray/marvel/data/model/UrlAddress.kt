package com.devmmurray.marvel.data.model

class UrlAddress {

    companion object {

        // Main URL parts
        const val BASE_URL = "https://gateway.marvel.com/"

        const val TS = "1"
        const val API_KEY = "4201114ad97a1d6e52819749ab48b380"
        const val HASH = "24762b64f879465e47f96b0ac6c31f3a"
        const val AUTH =
            "?ts=1&apikey=4201114ad97a1d6e52819749ab48b380&hash=24762b64f879465e47f96b0ac6c31f3a"

//   https://gateway.marvel.com/characters/1009351/?ts=1&apikey=4201114ad97a1d6e52819749ab48b380&hash=24762b64f879465e47f96b0ac6c31f3a

        // Poster Image Path
        const val POSTER = "/portrait_uncanny"

        // Thumbnail
        const val THUMBNAIL = "/standard_medium"
    }


    // To Search For A Single Character
    // https://gateway.marvel.com:443/v1/public/characters?name=
    // plus TS, API_KEY, HASH
}

enum class RetrofitFlags {
    SINGLE_CHARACTER,
    CHARACTER_LIST
}