package com.example.jogodeadvinhar

import kotlinx.coroutines.flow.Flow

class PontuacaoRepository(private val pontuacaoDAO: PontuacaoDAO) {

    fun buscarTodas(): Flow<List<Pontuacao>> {
        return pontuacaoDAO.buscarTodas()
    }

    suspend fun inserir(pontuacao: Pontuacao) {
        pontuacaoDAO.inserir(pontuacao)
    }


}