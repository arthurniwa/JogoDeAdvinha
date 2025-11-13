package com.example.jogodeadvinhar.data.repositorio

import com.example.jogodeadvinhar.data.local.ListaDAO
import com.example.jogodeadvinhar.data.model.Lista
import kotlinx.coroutines.flow.Flow

class ListaRepository(private val dao: ListaDAO) {

    fun getAllItens(): Flow<List<Lista>> = dao.buscarTodos()

    suspend fun inserir(item: Lista) = dao.inserir(item)

    suspend fun atualizar(item: Lista) = dao.atualizar(item)

    suspend fun deletar(item: Lista) = dao.deletar(item)
}