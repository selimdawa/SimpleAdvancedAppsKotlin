package com.flatcode.simpleadvancedapps.pokemon.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simpleadvancedapps.pokemon.data.database.entities.PokeDetailEntity
import com.flatcode.simpleadvancedapps.pokemon.data.database.entities.PokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeDao {

    @Query("SELECT * FROM pokemon_table ORDER BY id ASC")
    fun getAllPokemon(): Flow<List<PokeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokeEntity>)

    @Query("DELETE FROM pokemon_table")
    suspend fun clearTable()

    @Query("SELECT * FROM pokemon_detail_table WHERE id_int = :id")
    suspend fun getPokemonDetails(id: Int): PokeDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonDetail(pokemon: PokeDetailEntity)
}