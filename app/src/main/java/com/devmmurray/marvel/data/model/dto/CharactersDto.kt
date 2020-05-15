package com.devmmurray.marvel.data.model.dto

import com.devmmurray.marvel.data.model.UrlAddress.Companion.POSTER
import com.devmmurray.marvel.data.model.UrlAddress.Companion.THUMBNAIL
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDto(
    @Json(name = "data") val data: CharacterData?
)

@JsonClass(generateAdapter = true)
data class CharacterData(
    @Json(name = "results") val results: List<CharacterResults>?
)

@JsonClass(generateAdapter = true)
data class CharacterResults(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "thumbnail") val thumbnail: Thumbnail?,
    @Json(name = "comics") val comics: ComicsList?,
    @Json(name = "series") val series: SeriesList?
)

@JsonClass(generateAdapter = true)
data class Thumbnail(
    @Json(name = "path") val path: String?,
    @Json(name = "extension") val extension: String?
) {
    val thumbnail: String? by lazy { buildThumbnailUrl() }
    private fun buildThumbnailUrl(): String? {
        return "$path${THUMBNAIL}.$extension"
    }

    val poster: String? by lazy { buildPosterUrl() }
    private fun buildPosterUrl(): String? {
        return "$path${POSTER}.$extension"
    }
}

@JsonClass(generateAdapter = true)
data class ComicsList(
    @Json(name = "items") val items: List<ComicItems>?
)

@JsonClass(generateAdapter = true)
data class ComicItems(
    @Json(name = "resourceURI") val resourceURI: String?
) {
    val comicId: String? by lazy { buildComicId() }
    private fun buildComicId(): String? {
        val subUri = resourceURI?.substring(43)
        return "$subUri".trim()
    }
}

@JsonClass(generateAdapter = true)
data class SeriesList(
    @Json(name = "items") val items: List<SeriesItems>?
)

@JsonClass(generateAdapter = true)
data class SeriesItems(
    @Json(name = "resourceURI") val resourceURI: String?
) {
    val seriesId: String? by lazy { buildSeriesId() }
    private fun buildSeriesId(): String? {
        val subUri = resourceURI?.substring(43)
        return "$subUri".trim()
    }
}
