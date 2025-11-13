package com.example.jogodeadvinhar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.jogodeadvinhar.PontuacaoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class Imagem(
    val id: Int,
    val diferente: Boolean,
    val revelada: Boolean = false
)

data class JogoUiState(
    val imagens: List<Imagem> = emptyList(),
    val acertos: Int = 0,
    val tentativas: Int = 0,
    val jogoFinalizado: Boolean = false,
    val maiorPontuacao: Int = 0
)

class JogoViewModel(private val repository: PontuacaoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(JogoUiState())
    val uiState: StateFlow<JogoUiState> = _uiState.asStateFlow()

    private val total = 6

    init {
        viewModelScope.launch {
            repository.buscarTodas().collect { pontuacoes ->
                val pontuacaoAcumulada = pontuacoes.find { it.id == 1 }?.acertos ?: 0
                _uiState.update { currentState ->
                    currentState.copy(maiorPontuacao = pontuacaoAcumulada)
                }
            }
        }
        embaralhar()
    }

    fun embaralhar() {
        val lista = MutableList(total) { Imagem(it, false, revelada = false) }
        val posicaoDiferente = (0 until total).random()
        lista[posicaoDiferente] = Imagem(posicaoDiferente, true, revelada = false)
        _uiState.update { currentState ->
            currentState.copy(
                imagens = lista.shuffled(),
                acertos = 0,
                tentativas = 0,
                jogoFinalizado = false
            )
        }
    }

    fun revelarImagem(id: Int) {
        val currentState = _uiState.value
        val listaAtual = currentState.imagens.toMutableList()
        val index = listaAtual.indexOfFirst { it.id == id }

        if (index != -1 && !listaAtual[index].revelada) {
            listaAtual[index] = listaAtual[index].copy(revelada = true)

            val imagem = listaAtual[index]
            val acerto = if (imagem.diferente) 1 else 0

            val novoJogoFinalizado = acerto > 0

            // calcula o score acumulado (maiorPontuacao)
            val scoreAcumuladoParaSalvar = if (novoJogoFinalizado) {
                currentState.maiorPontuacao + 1
            } else {
                currentState.maiorPontuacao
            }

            _uiState.update {
                it.copy(
                    imagens = listaAtual,
                    acertos = it.acertos + acerto,
                    tentativas = currentState.tentativas + 1,
                    jogoFinalizado = novoJogoFinalizado,
                    maiorPontuacao = scoreAcumuladoParaSalvar // Atualiza o placar
                )
            }

            // Salva o NOVO TOTAL acumulado no DB
            if (novoJogoFinalizado) {
                viewModelScope.launch {
                    val pontuacaoASalvar = Pontuacao(
                        id = 1, // ID fixo para o placar acumulado
                        jogador = "Jogador",
                        acertos = scoreAcumuladoParaSalvar // Salva o novo total acumulado
                    )
                    // O DAO (com OnConflictStrategy.REPLACE) fará o UPSERT (Update/Insert)
                    repository.inserir(pontuacaoASalvar)
                }
            }
        }
    }
}

class JogoViewModelFactory(private val repository: PontuacaoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JogoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JogoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}