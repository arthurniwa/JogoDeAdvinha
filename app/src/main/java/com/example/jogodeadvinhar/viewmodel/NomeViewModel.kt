package com.example.jogodeadvinhar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.jogodeadvinhar.data.model.Nome
import com.example.jogodeadvinhar.data.repositorio.NomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// -------------------------
// UI STATE
// -------------------------
data class NomeUiState(
    val nome: String = "",
    val modoEdicao: Boolean = false,
    val status: String = "Ativo"
) {
    val textoBotao: String
        get() = if (modoEdicao) "Salvar Nome" else "Editar Nome"
}

// -------------------------
// VIEWMODEL
// -------------------------
class NomeViewModel(private val repository: NomeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(NomeUiState())
    val uiState: StateFlow<NomeUiState> = _uiState.asStateFlow()

    init {
        carregarPerfil()
    }

    private fun carregarPerfil() {
        viewModelScope.launch {
            val perfilSalvo = withContext(Dispatchers.IO) {
                repository.buscarPerfil()
            }

            if (perfilSalvo == null) {
                // Se não houver nome no BD, cria um padrão
                withContext(Dispatchers.IO) {
                    repository.inserir(Nome(id = 1, nome = "VINICIUS LEMES"))
                }
                _uiState.update { it.copy(nome = "VINICIUS LEMES") }
            } else {
                _uiState.update { it.copy(nome = perfilSalvo.nome) }
            }
        }
    }

    fun onNomeChange(novoNome: String) {
        _uiState.update { it.copy(nome = novoNome.uppercase()) }
    }

    fun alternarModoEdicao() {
        _uiState.update { it.copy(modoEdicao = !it.modoEdicao) }
    }

    fun salvarNome() {
        val nomeAtual = _uiState.value.nome
        if (nomeAtual.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            repository.atualizar(Nome(id = 1, nome = nomeAtual))
        }

        _uiState.update { it.copy(modoEdicao = false) }
    }
}

// -------------------------
// FACTORY
// -------------------------
class NomeViewModelFactory(private val repository: NomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}