package com.devmmurray.marvel.data.model.domain

// Full Character Object
class CharacterObject(
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnailLink: String?,
    val posterImage: String?,
    val comics: List<ComicStub>?,
    val series: List<SeriesStub>?
)

class ComicLink(
    val comicUrl: String?
)

class SeriesLink(
    val seriesUrl: String?
)

// Character Stub for use with Recycler Views
class CharacterStub(
    val id: Int?,
    val name: String?,
    val thumbnailLink: String?
)