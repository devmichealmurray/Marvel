package com.devmmurray.marvel.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmmurray.marvel.data.model.entities.ComicDateEntity
import com.devmmurray.marvel.data.model.entities.ComicsCharacterEntity
import com.devmmurray.marvel.data.model.entities.ComicsEntity

@Dao
interface ComicsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComic(comic: ComicsEntity)

    @Query("SELECT * FROM comics WHERE marvelId = :marvelId")
    suspend fun getComicByMarvelId(marvelId: Int): ComicsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComicCharacter(character: ComicsCharacterEntity)

    @Query("SELECT * FROM comic_characters WHERE comic_id = :comicId")
    suspend fun getComicCharacters(comicId: Int): MutableList<ComicsCharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComicDate(date: ComicDateEntity)

    @Query("SELECT * FROM comic_dates WHERE comic_id = :comicId ")
    suspend fun getComicDate(comicId: Int): MutableList<ComicDateEntity>

    // Check if Database is empty
    @Query("SELECT * FROM comics LIMIT 1")
    suspend fun checkComicsDatabase(): ComicsEntity

    @Query("SELECT COUNT(uid) FROM comics")
    suspend fun countComicsDatabase(): Int?
}