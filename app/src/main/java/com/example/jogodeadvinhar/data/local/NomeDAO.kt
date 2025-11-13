package com.example.jogodeadvinhar.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jogodeadvinhar.data.model.Nome

@Dao
interface NomeDAO {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun inserir(nome: Nome)

    @Update
    suspend fun atualizar(nome: Nome)

    @Query("SELECT * FROM perfil_tabela WHERE id = 1")
    suspend fun buscarPerfil() : Nome?

    @Delete
    suspend fun deletar(nome: Nome)

}