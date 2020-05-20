package com.devmmurray.marvel.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmmurray.marvel.data.model.entities.CharacterComicsEntity
import com.devmmurray.marvel.data.model.entities.CharacterEntity
import com.devmmurray.marvel.data.model.entities.CharacterSeriesEntity

const val DATABASE_SCHEMA_VERSION = 1
const val DB_NAME = "characters-db"

@Database(
    version = DATABASE_SCHEMA_VERSION,
    entities = [CharacterEntity::class, CharacterComicsEntity::class, CharacterSeriesEntity::class ],
    exportSchema = false)
abstract class CharactersDBClient: RoomDatabase() {

    abstract fun characterDao(): ICharacterDAO

    companion object {
        private var instance: CharactersDBClient? = null

        private fun createDatabase(context: Context): CharactersDBClient {
            return Room
                .databaseBuilder(context, CharactersDBClient::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        fun getDbInstance(context: Context): CharactersDBClient =
            (instance ?: createDatabase(context)). also {
                instance = it
            }
    }

}