package com.devmmurray.marvel.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmmurray.marvel.data.model.entities.*

const val DATABASE_SCHEMA_VERSION = 1
const val DB_NAME = "characters-db"

@Database(
    version = DATABASE_SCHEMA_VERSION,
    entities = [CharacterEntity::class, CharacterComicsEntity::class, CharacterSeriesEntity::class,
        ComicsEntity::class, ComicsCharacterEntity::class, ComicDateEntity::class,
        SeriesEntity::class, SeriesCharacterEntity::class, SeriesComicEntity::class],
    exportSchema = false
)
abstract class RoomDatabaseClient : RoomDatabase() {

    abstract fun characterDao(): CharacterDAO
    abstract fun comicsDao(): ComicsDAO
    abstract fun seriesDao(): SeriesDAO

    companion object {
        private var instance: RoomDatabaseClient? = null

        private fun createDatabase(context: Context): RoomDatabaseClient {
            return Room
                .databaseBuilder(context, RoomDatabaseClient::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        fun getDbInstance(context: Context): RoomDatabaseClient =
            (instance ?: createDatabase(context)).also {
                instance = it
            }
    }

}