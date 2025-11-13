package com.example.jogodeadvinhar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy // necessário para OnConflictStrategy
import com.example.jogodeadvinhar.data.model.Pontuacao
import kotlinx.coroutines.flow.Flow

@Dao
interface PontuacaoDAO {

    // inserir o registro com ID=1 (o placar acumulado)
    // e ele já existir, o Room o substituirá em vez de causar um crash.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(pontuacao: Pontuacao)

    @Query("SELECT * FROM pontuacoes ORDER BY acertos DESC")
    fun buscarTodas(): Flow<List<Pontuacao>>

}