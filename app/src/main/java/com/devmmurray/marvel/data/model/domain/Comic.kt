package com.devmmurray.marvel.data.model.domain

class Comic(
    val uid: Long = 0L,
    val marvelId: Int?,
    val title: String?,
    val issueNumber: Double?,
    val description: String?,
    val isbn: String?,
    val pageCount: Int?,
    val series: String?,
    val thumbnail: String?
)

class ComicCharacter(
    // id generate by room
    val uid: Long = 0L,
    // id of character it belongs to
    val comicId: Int?,
    // the Marvel id
    val characterId: String?
)

class ComicDate(
    val uid: Long = 0L,
    val comicId: Int?,
    val type: String?,
    val date: String?
)

