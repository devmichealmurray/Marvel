package com.devmmurray.marvel.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmmurray.marvel.data.model.domain.Series
import com.devmmurray.marvel.data.model.domain.SeriesCharacter
import com.devmmurray.marvel.data.model.domain.SeriesComic

@Entity(tableName = "series")
class SeriesEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "marvel_id")
    val marvelId:Int?,
    @ColumnInfo(name = "created_at")
    val timeStamp: Long?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "start_year")
    val startYear:Int?,
    @ColumnInfo(name = "end_year")
    val endYear: Int?,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String?
) {
    companion object{
        fun fromSeriesObject(series: Series) =
            SeriesEntity(
                series.uid,
                series.marvelId,
                series.timeStamp,
                series.title,
                series.description,
                series.startYear,
                series.endYear,
                series.thumbnail
            )
    }

    fun toSeriesObject() =
        Series(uid, marvelId, timeStamp, title, description, startYear, endYear, thumbnail)
}

@Entity(tableName = "series_characters")
class SeriesCharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "series_id")
    val seriesId: Int?,
    @ColumnInfo(name = "character_id")
    val characterId: String?
) {
    companion object{
        fun fromSeriesCharacter(character: SeriesCharacter) =
            SeriesCharacterEntity(character.uid, character.seriesId, character.characterId)
    }

    fun toSeriesCharacter() =
        SeriesCharacter(uid, seriesId, characterId)
}

@Entity(tableName = "series_comics")
class SeriesComicEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "series_id")
    val seriesId: Int?,
    @ColumnInfo(name = "comic_id")
    val comicId: String?
) {
    companion object{
        fun fromSeriesComic(comic: SeriesComic) =
            SeriesComicEntity(comic.uid, comic.seriesId, comic.comicId)
    }

    fun toSeriesComic() =
        SeriesComic(uid, seriesId, comicId)
}