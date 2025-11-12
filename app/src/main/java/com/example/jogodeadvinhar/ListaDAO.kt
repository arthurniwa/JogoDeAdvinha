package com.example.jogodeadvinhar

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ListaDAO {

    @Insert
    suspend fun inserir(item: Lista)

    @Update
    suspend fun atualizar(item: Lista)

    @Delete
    suspend fun deletar(item: Lista)

    @Query("SELECT * FROM lista ORDER BY id DESC")
    fun buscarTodos(): Flow<List<Lista>>
}