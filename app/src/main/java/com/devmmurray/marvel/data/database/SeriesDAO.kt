package com.devmmurray.marvel.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmmurray.marvel.data.model.entities.SeriesCharacterEntity
import com.devmmurray.marvel.data.model.entities.SeriesComicEntity
import com.devmmurray.marvel.data.model.entities.SeriesEntity

@Dao
interface SeriesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSeries(series: SeriesEntity)

    @Query("SELECT * From series WHERE marvel_id = :marvelId")
    suspend fun getSeriesByMarvelId(marvelId: Int): SeriesEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSeriesCharacter(character: SeriesCharacterEntity)

    @Query("SELECT * FROM series_characters WHERE series_id = :seriesId")
    suspend fun getSeriesCharacters(seriesId: Int): MutableList<SeriesCharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSeriesComic(comic: SeriesComicEntity)

    @Query("SELECT * FROM series_comics WHERE series_id = :seriesId")
    suspend fun getSeriesComics(seriesId: Int): MutableList<SeriesComicEntity>

    // Database Check
    @Query("SELECT * FROM series LIMIT 1")
    suspend fun checkSeriesDatabase(): SeriesEntity

    @Query("SELECT COUNT(uid) FROM series")
    suspend fun countSeriesDatabase(): Int?
}