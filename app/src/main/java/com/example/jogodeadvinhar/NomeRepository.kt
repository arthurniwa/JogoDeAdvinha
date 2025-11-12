package com.example.jogodeadvinhar

import com.example.jogodeadvinhar.Nome
import com.example.jogodeadvinhar.NomeDAO

class NomeRepository(private val nomeDao: NomeDAO) {

    // só tem um (id=1) suspend serve
    suspend fun buscarPerfil(): Nome? {
        return nomeDao.buscarPerfil()
    }

    suspend fun inserir(nome: Nome) {
        nomeDao.inserir(nome)
    }

    suspend fun atualizar(nome: Nome) {
        nomeDao.atualizar(nome)
    }

}