package com.devmmurray.marvel.data.repository

import com.devmmurray.marvel.data.database.CharacterDAO
import com.devmmurray.marvel.data.database.ComicsDAO
import com.devmmurray.marvel.data.database.SeriesDAO
import com.devmmurray.marvel.data.model.entities.*

class DatabaseRepository(
    private val characterDataSource: CharacterDAO,
    private val comicsDataSource: ComicsDAO,
    private val seriesDataSource: SeriesDAO
) {

    /**
     *  Character Database Functions
     */
    suspend fun addCharacter(character: CharacterEntity) =
        characterDataSource.addCharacter(character)

    suspend fun getCharacterByMarvelId(id: Int) =
        characterDataSource.getCharacterByMarvelId(id).toCharacterObject()


    suspend fun addCharacterComic(comic: CharacterComicsEntity) =
        characterDataSource.addCharacterComic(comic)

    suspend fun getCharacterComics(id: Int) =
        characterDataSource.getCharacterComics(id).map { it.toCharactersComic() }


    suspend fun addCharacterSeries(series: CharacterSeriesEntity) =
        characterDataSource.addCharacterSeries(series)

    suspend fun getCharacterSeries(id: Int) =
        characterDataSource.getCharacterSeries(id).map { it.toCharacterSeries() }

    suspend fun checkCharacterDatabase() =
        characterDataSource.checkCharacterDatabase()

    /**
     *  Comics Database Functions
     */

    suspend fun addComic(comic: ComicsEntity) =
        comicsDataSource.addComic(comic)

    suspend fun getComicByMarvelId(id: Int) =
        comicsDataSource.getComicByMarvelId(id).toComicObject()

    suspend fun addComicCharacter(character: ComicsCharacterEntity) =
        comicsDataSource.addComicCharacter(character)

    suspend fun getComicCharacters(comicId: Int) =
        comicsDataSource.getComicCharacters(comicId).map { it.toComicCharacter() }

    suspend fun addComicDate(date: ComicDateEntity) =
        comicsDataSource.addComicDate(date)

    suspend fun getComicDate(comicId: Int) =
        comicsDataSource.getComicDate(comicId).map { it.toComicDate() }

    suspend fun checkComicsDatabase() =
        comicsDataSource.checkComicsDatabase().toComicObject()


    /**
     *  Series Database Functions
     */

    suspend fun addSeries(series: SeriesEntity) =
        seriesDataSource.addSeries(series)

    suspend fun getSeriesByMarvelId(marvelId: Int) =
        seriesDataSource.getSeriesByMarvelId(marvelId).toSeriesObject()

    suspend fun addSeriesCharacter(character: SeriesCharacterEntity) =
        seriesDataSource.addSeriesCharacter(character)


    suspend fun getSeriesCharacter(seriesId: Int) =
        seriesDataSource.getSeriesCharacters(seriesId).map { it.toSeriesCharacter() }

    suspend fun addSeriesComic(comic: SeriesComicEntity) =
        seriesDataSource.addSeriesComic(comic)

    suspend fun getSeriesComic(seriesId: Int) =
        seriesDataSource.getSeriesComics(seriesId).map { it.toSeriesComic() }

    suspend fun checkSeriesDatabase() =
        seriesDataSource.checkSeriesDatabase().toSeriesObject()
}