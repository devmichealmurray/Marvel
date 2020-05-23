package com.devmmurray.marvel.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.data.model.domain.CharactersComic
import com.devmmurray.marvel.data.model.domain.CharactersSeries

// Character Object to be stored in Database for use in UI.
// This prevents exceeding daily limit on API calls to Marvel

@Entity(tableName = "characters")
class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "marvelId")
    val marvelId: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String?,
    @ColumnInfo(name = "poster")
    val poster: String?
) {
    companion object {

        fun fromCharacterObject(character: CharacterObject) =
            CharacterEntity(
                character.uid,
                character.marvelId,
                character.name,
                character.description,
                character.thumbnail,
                character.poster
            )
    }

    fun toCharacterObject() =
        CharacterObject(
            uid,
            marvelId,
            name,
            description,
            thumbnail,
            poster
        )
}

@Entity(tableName = "character_comics")
class CharacterComicsEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "character_id")
    val characterId: Int?,
    @ColumnInfo(name = "comic_id")
    val comicId: String?
) {
    companion object {

        fun fromCharacterComics(comic: CharactersComic) =
            CharacterComicsEntity(
                comic.uid,
                comic.characterId,
                comic.comicId
            )
    }

    fun toCharactersComic() =
        CharactersComic(
            uid,
            characterId,
            comicId
        )
}

@Entity(tableName = "character_series")
class CharacterSeriesEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "character_id")
    val characterId: Int?,
    @ColumnInfo(name = "series_id")
    val seriesId: String?
) {
    companion object {

        fun fromCharacterSeries(series: CharactersSeries) =
            CharacterSeriesEntity(
                series.id,
                series.characterId,
                series.seriesId
            )
    }

    fun toCharacterSeries() =
        CharactersSeries(
            uid,
            characterId,
            seriesId
        )
}