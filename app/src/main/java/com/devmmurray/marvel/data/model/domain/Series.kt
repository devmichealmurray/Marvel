package com.devmmurray.marvel.data.model.domain

// Full Series Object
class Series(
    val uid: Long = 0L,
    val marvelId: Int?,
    val title: String?,
    val description: String?,
    val startYear: Int?,
    val endYear: Int?,
    val thumbnail: String?
)


class SeriesCharacter(
    val uid: Long = 0L,
    val seriesId: Int?,
    val characterId: Int?
)

class SeriesComic(
    val uid: Long = 0L,
    val seriesId: Int?,
    val comicId: Int?
)
