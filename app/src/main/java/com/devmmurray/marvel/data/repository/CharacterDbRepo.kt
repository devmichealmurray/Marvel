package com.devmmurray.marvel.data.repository

import com.devmmurray.marvel.data.database.ICharacterDAO
import com.devmmurray.marvel.data.model.entities.CharacterComicsEntity
import com.devmmurray.marvel.data.model.entities.CharacterEntity
import com.devmmurray.marvel.data.model.entities.CharacterSeriesEntity

class CharacterDbRepo(private val dataSource: ICharacterDAO) {

    suspend fun addCharacter(character: CharacterEntity) =
        dataSource.addCharacter(character)

    suspend fun getCharacterByMarvelId(id: Int) =
        dataSource.getCharacterByMarvelId(id).toCharacterObject()


    suspend fun addCharacterComic(comic: CharacterComicsEntity) =
        dataSource.addCharacterComic(comic)

    suspend fun getCharacterComics(id: Int) =
        dataSource.getCharacterComics(id).map { it.toCharacterComicsObject() }


    suspend fun addCharacterSeries(series: CharacterSeriesEntity) =
        dataSource.addCharacterSeries(series)

    suspend fun getCharacterSeries(id: Int) =
        dataSource.getCharacterSeries(id).map { it.toCharacterSeriesObject() }


    suspend fun checkDatabase() =
        dataSource.checkDatabase()
}