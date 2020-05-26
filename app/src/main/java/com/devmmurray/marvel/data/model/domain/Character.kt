package com.devmmurray.marvel.data.model.domain

// Full Character Object
class CharacterObject(
    var uid: Long = 0L,
    val marvelId: Int?,
    val timeStamp: Long?,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val poster: String?
)

class CharactersComic(
    // id generate by room
    val uid: Long = 0L,
    // id of character it belongs to
    val characterId: Int?,
    // the Marvel id of the comic
    val comicId: Int?
)

class CharactersSeries(
    val id: Long = 0L,
    val characterId: Int?,
    val seriesId: Int?
)

