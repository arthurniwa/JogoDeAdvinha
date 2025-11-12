package com.example.jogodeadvinhar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ListaUiState(
    val textoInput: String = "",
    val itens: List<Lista> = emptyList(),
    val itemEmEdicao: Lista? = null
) {
    val textoBotao: String
        get() = if (itemEmEdicao == null) "Adicionar" else "Atualizar"
}

class AlterarListaViewModel(private val repository: ListaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ListaUiState())
    val uiState: StateFlow<ListaUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllItens().collect { itens ->
                _uiState.update { it.copy(itens = itens) }
            }
        }
    }

    fun onTextoInputChange(novoTexto: String) {
        _uiState.update { it.copy(textoInput = novoTexto) }
    }

    fun onAdicionarOuAtualizar() {
        val state = _uiState.value
        if (state.textoInput.isBlank()) return

        val item = state.itemEmEdicao?.copy(nome = state.textoInput) ?: Lista(nome = state.textoInput)

        viewModelScope.launch {
            if (state.itemEmEdicao == null) {
                repository.inserir(item)
            } else {
                repository.atualizar(item)
            }
            limparCampos()
        }
    }

    fun onEditar(item: Lista) {
        _uiState.update {
            it.copy(
                textoInput = item.nome,
                itemEmEdicao = item
            )
        }
    }

    fun onDeletar(item: Lista) {
        viewModelScope.launch { repository.deletar(item) }
    }

    private fun limparCampos() {
        _uiState.update { it.copy(textoInput = "", itemEmEdicao = null) }
    }
}

class AlterarListaViewModelFactory(private val repository: ListaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlterarListaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlterarListaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}