package com.devmmurray.marvel.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmmurray.marvel.data.model.domain.CharacterComicsList
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.data.model.domain.CharacterSeriesList

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
            CharacterObject(
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
    @ColumnInfo
    val characterId: Int?,
    @ColumnInfo
    val comicId: String?
) {
    companion object {

        fun fromCharacterComicsObject(comic: CharacterComicsList) =
            CharacterComicsList(
                comic.uid,
                comic.characterId,
                comic.comicId
            )
    }

    fun toCharacterComicsObject() =
        CharacterComicsList(
            uid,
            characterId,
            comicId
        )
}

@Entity(tableName = "character_series")
class CharacterSeriesEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo
    val characterId: Int?,
    @ColumnInfo
    val seriesId: String?
) {
    companion object {

        fun fromCharacterSeriesObject(series: CharacterSeriesList) =
            CharacterSeriesList(
                series.id,
                series.characterId,
                series.seriesId
            )
    }

    fun toCharacterSeriesObject() =
        CharacterSeriesList(
            uid,
            characterId,
            seriesId
        )
}