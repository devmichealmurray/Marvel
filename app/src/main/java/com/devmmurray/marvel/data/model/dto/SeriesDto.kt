package com.devmmurray.marvel.data.model.dto

import com.devmmurray.marvel.data.model.UrlAddress
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
    @Json(name = "id") val marvelId: Int?,
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
    val thumbnail: String? by lazy { buildThumbnailUrl() }
    private fun buildThumbnailUrl(): String? {
        return "$path${UrlAddress.THUMBNAIL}.$extension"
    }

    val poster: String? by lazy { buildPosterUrl() }
    private fun buildPosterUrl(): String? {
        return "$path${UrlAddress.POSTER}.$extension"
    }
}


@JsonClass(generateAdapter = true)
data class SeriesCharacters(
    @Json(name = "items") val seriesCharacter: List<CharacterUrls>?
)


@JsonClass(generateAdapter = true)
data class CharacterUrls(
    @Json(name = "resourceURI") val resourceURI: String?
) {
    val characterId: String? by lazy { buildCharacterId() }
    private fun buildCharacterId(): String? {
        val subUri = resourceURI?.substring(47)
        return "$subUri".trim()
    }
}


@JsonClass(generateAdapter = true)
data class Comics(
    @Json(name = "items") val comicItems: List<ComicUrls>
)


@JsonClass(generateAdapter = true)
data class ComicUrls(
    @Json(name = "resourceURI") val resourceURI: String?
) {
    val comicId: String? by lazy { buildComicId() }
    private fun buildComicId(): String? {
        val subUri = resourceURI?.substring(43)
        return "$subUri".trim()
    }
}