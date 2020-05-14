package com.devmmurray.marvel.data.model.dto

import com.devmmurray.marvel.data.model.UrlAddress
import com.devmmurray.marvel.data.model.UrlAddress.Companion.AUTH
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeriesDto(
    @Json(name = "data") val data: SeriesData?
)

@JsonClass(generateAdapter = true)
data class SeriesData(
    @Json(name = "results") val results: List<SeriesResults>?
)

@JsonClass(generateAdapter = true)
data class SeriesResults(
    @Json(name = "id") val id: Int?,
    @Json(name = "title") val title: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "startYear") val startYear: Int?,
    @Json(name = "endYear") val endYear: Int,
    @Json(name = "thumbnail") val thumbnail: SeriesThumbnail?,
    @Json(name = "characters") val characters: SeriesCharacters?,
    @Json(name = "comics") val comics: Comics?

)


@JsonClass(generateAdapter = true)
data class SeriesThumbnail(
    @Json(name = "path") val path: String?,
    @Json(name = "extension") val extension: String?
) {
    val square: String? by lazy { buildSquareUrl() }
    private fun buildSquareUrl(): String? {
        return "$path${UrlAddress.LARGE_SQUARE}$extension"
    }
}


@JsonClass(generateAdapter = true)
data class SeriesCharacters(
    @Json(name = "items") val seriesCharacter: List<CharacterUrls>?
)

@JsonClass(generateAdapter = true)
data class CharacterUrls(
    @Json(name = "resourceURI") val resourceURI: String?,
    @Json(name = "name") val characterName: String
) {
    val characterUrl: String? by lazy { buildCharacterUrl() }
    private fun buildCharacterUrl(): String? {
        val subUri = resourceURI?.substring(36)
        return "$subUri${AUTH}"
    }
}

@JsonClass(generateAdapter = true)
data class Comics(
    @Json(name = "items") val comicItems: List<ComicUrls>
)

@JsonClass(generateAdapter = true)
data class ComicUrls(
    @Json(name = "resourceURI") val resourceURI: String?,
    @Json(name = "name") val characterName: String
) {
    val comicUrls: String? by lazy { buildComicUrl() }
    private fun buildComicUrl(): String? {
        val subUri = resourceURI?.substring(36)
        return "$subUri${AUTH}"
    }
}