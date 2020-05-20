package com.devmmurray.marvel.data.model.domain

class Comic(
    val id: Int?,
    val title: String?,
    val issueNumber: String?,
    val description: String?,
    val isbn: String?,
    val upc: String?,
    val pageCount: Int,
    val series: Series,
    val onSaleDate: String?,
    val thumbnail: String?,
    val posterImages: List<PosterImage>?,
    val characterLinks: List<String>,
    val characters: List<CharacterObject>
)


class ComicStub(
    val id: Int?,
    val title: String?,
    val thumbnail: String?
)