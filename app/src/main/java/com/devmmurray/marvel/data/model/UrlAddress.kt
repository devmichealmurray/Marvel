package com.devmmurray.marvel.data.model

class UrlAddress {

    companion object {

        // Main URL parts
        const val BASE_URL = "http://gateway.marvel.com/"

        const val TS = "1"
        const val API_KEY = "4201114ad97a1d6e52819749ab48b380"
        const val HASH = "24762b64f879465e47f96b0ac6c31f3a"
        const val AUTH =
            "?ts=1&apikey=4201114ad97a1d6e52819749ab48b380&hash=24762b64f879465e47f96b0ac6c31f3a"


        // Portrait Image Path
        const val MEDIUM = "portrait_medium"
        const val UNCANNY = "portrait_uncanny"

        // Square Image Path
        const val LARGE_SQUARE = "standard_large"
        const val FANTASTIC_SQUARE = "standard_fantasitc"

    }

    // To Search For A Single Character
    // https://gateway.marvel.com:443/v1/public/characters?name=
    // plus TS, API_KEY, HASH
}