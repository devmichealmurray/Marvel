package com.devmmurray.marvel.data.model.dto

import com.devmmurray.marvel.data.model.UrlAddress
import com.devmmurray.marvel.data.model.UrlAddress.Companion.AUTH
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicDto(
    @Json(name = "data") val data: ComicData?
)

@JsonClass(generateAdapter = true)
data class ComicData(
    @Json(name = "results") val results: List<ComicResults>?
)

@JsonClass(generateAdapter = true)
data class ComicResults(
    @Json(name = "id") val id: Int?,
    @Json(name = "title") val title: String?,
    @Json(name = "issueNumber") val issueNumber: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "isbn") val isbn: String?,
    @Json(name = "upc") val upc: String?,
    @Json(name = "pageCount") val pageCount: Int?,
    @Json(name = "series") val series: ComicSeries?,
    @Json(name = "dates") val dates: List<ComicDates>?,
    @Json(name = "thumbnail") val thumbnail: ComicThumbnail?,
    @Json(name = "images") val image: List<ComicImages>?,
    @Json(name = "characters") val characters: CharacterItems?
)

@JsonClass(generateAdapter = true)
data class ComicSeries(
    @Json(name = "resourceURI") val resourceURI: String?,
    @Json(name = "name") val name: String?
) {
    val series: String? by lazy { buildSeriesUrl() }
    private fun buildSeriesUrl(): String? {
        val subUri = resourceURI?.substring(36)
        return "$subUri${AUTH}"
    }
}

@JsonClass(generateAdapter = true)
data class ComicDates(
    @Json(name = "type") val type: String?,
    @Json(name = "date") val date: String?
)

@JsonClass(generateAdapter = true)
data class ComicThumbnail(
    @Json(name = "path") val path: String?,
    @Json(name = "extension") val extension: String?
) {
    val square: String? by lazy { buildSquareUrl() }
    private fun buildSquareUrl(): String? {
        return "$path${UrlAddress.LARGE_SQUARE}$extension"
    }
}

@JsonClass(generateAdapter = true)
data class ComicImages(
    @Json(name = "path") val path: String?,
    @Json(name = "extension") val extension: String?
) {
    val poster: String? by lazy { buildPosterUrl() }
    private fun buildPosterUrl(): String? {
        return "$path${UrlAddress.UNCANNY}$extension"
    }
}

@JsonClass(generateAdapter = true)
data class Characters(
    @Json(name = "items") val items: List<CharacterItems>?
)

@JsonClass(generateAdapter = true)
data class CharacterItems(
    @Json(name = "resourceURI") val resourceURI: String?,
    @Json(name = "name") val name: String?
) {
    val character: String? by lazy { buildCharacterUrl() }
    private fun buildCharacterUrl(): String? {
        val subUri = resourceURI?.substring(36)
        return "$subUri${AUTH}"
    }
}
