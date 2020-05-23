package com.devmmurray.marvel.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.data.model.entities.CharacterComicsEntity
import com.devmmurray.marvel.data.model.entities.CharacterEntity
import com.devmmurray.marvel.data.model.entities.CharacterSeriesEntity

@Dao
interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(character: CharacterEntity)

    @Query("SELECT * FROM characters WHERE marvelId = :marvelId")
    suspend fun getCharacterByMarvelId(marvelId: Int): CharacterEntity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacterComic(comic: CharacterComicsEntity)

    @Query("SELECT * FROM character_comics WHERE character_id = :characterId")
    suspend fun getCharacterComics(characterId: Int): MutableList<CharacterComicsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacterSeries(series: CharacterSeriesEntity)

    @Query("SELECT * FROM character_series WHERE character_id = :characterId ")
    suspend fun getCharacterSeries(characterId: Int): MutableList<CharacterSeriesEntity>

    // Check if Database is empty
    @Query("SELECT * FROM characters LIMIT 1")
    suspend fun checkCharacterDatabase(): CharacterObject
}