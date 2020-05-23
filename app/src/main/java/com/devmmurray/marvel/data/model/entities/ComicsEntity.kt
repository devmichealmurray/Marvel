package com.devmmurray.marvel.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmmurray.marvel.data.model.domain.Comic
import com.devmmurray.marvel.data.model.domain.ComicCharacter
import com.devmmurray.marvel.data.model.domain.ComicDate

@Entity(tableName = "comics")
class ComicsEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "marvelId")
    val marvelId: Int?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "issue_number")
    val issueNumber: Double?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "isbn")
    val isbn: String?,
    @ColumnInfo(name = "page_count")
    val pageCount: Int?,
    @ColumnInfo(name = "series")
    val series: String?,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String?
) {
    companion object {
        fun fromComicObject(comic: Comic) =
            ComicsEntity(
                comic.uid,
                comic.marvelId,
                comic.title,
                comic.issueNumber,
                comic.description,
                comic.isbn,
                comic.pageCount,
                comic.series,
                comic.thumbnail
            )
    }

    fun toComicObject() =
        Comic(uid, marvelId, title, issueNumber, description, isbn, pageCount, series, thumbnail)
}

@Entity(tableName = "comic_characters")
class ComicsCharacterEntity(
    // id generate by room
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    // id of comic it belongs to
    @ColumnInfo(name = "comic_id")
    val comicId: Int?,
    // id of character
    @ColumnInfo(name = "character_id")
    val characterId: String?
) {
    companion object {
        fun fromComicsCharacter(character: ComicCharacter) =
            ComicsCharacterEntity(
                character.uid,
                character.comicId,
                character.characterId
            )
    }

    fun toComicCharacter() =
        ComicCharacter(uid, comicId, characterId)
}


@Entity(tableName = "comic_dates")
class ComicDateEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "comic_id")
    val comicId: Int?,
    @ColumnInfo(name = "type")
    val type: String?,
    @ColumnInfo(name = "date")
    val date: String?
){
    companion object {
        fun fromComicDate(date: ComicDate) =
            ComicDateEntity(
                date.uid, date.comicId, date.type, date.date
            )
    }

    fun toComicDate() =
        ComicDate(uid, comicId, type, date)

}


