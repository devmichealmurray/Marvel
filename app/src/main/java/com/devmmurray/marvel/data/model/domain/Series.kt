package com.devmmurray.marvel.data.model.domain

// Full Series Object
class Series(
    val id: Int?,
    val title: String,
    val description: String?,
    val startYear: Int?,
    val endYear: Int?,
    val thumbnail: String
//    val characters: List<CharacterStub>?,
//    val comics: List<ComicStub>
)

// Character Stub for use with Recycler Views
class SeriesStub(
    val id: Int?,
    val title: String?,
    val thumbnail: String?
)