package com.example.jogodeadvinhar.data.repositorio

import com.example.jogodeadvinhar.data.local.PontuacaoDAO
import com.example.jogodeadvinhar.data.model.Pontuacao
import kotlinx.coroutines.flow.Flow

class PontuacaoRepository(private val pontuacaoDAO: PontuacaoDAO) {

    fun buscarTodas(): Flow<List<Pontuacao>> {
        return pontuacaoDAO.buscarTodas()
    }

    suspend fun inserir(pontuacao: Pontuacao) {
        pontuacaoDAO.inserir(pontuacao)
    }


}