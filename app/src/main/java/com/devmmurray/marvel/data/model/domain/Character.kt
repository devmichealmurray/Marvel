package com.devmmurray.marvel.data.model.domain

// Full Character Object
class CharacterObject(
    var uid: Long = 0L,
    val marvelId: Int?,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val poster: String?
)

class CharacterComicsList(
    // id generate by room
    val uid: Long = 0L,
    // id of character it belongs to
    val characterId: Int?,
    // the Marvel id
    val comicId: String?
)

class CharacterSeriesList(
    val id: Long = 0L,
    val characterId: Int?,
    val seriesId: String?
)

