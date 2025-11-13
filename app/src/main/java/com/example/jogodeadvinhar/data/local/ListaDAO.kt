package com.example.jogodeadvinhar.data.local

import androidx.room.*
import com.example.jogodeadvinhar.data.model.Lista
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