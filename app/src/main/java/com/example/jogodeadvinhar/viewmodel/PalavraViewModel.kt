package com.example.jogodeadvinhar.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PalavraViewModel : ViewModel() {

    private val todasPalavras = listOf(
        "Peixe",
        "Tigre",
        "Elefante",
        "Girafa",
        "Macaco",
        "Cachorro",
        "Gato",
        "Coelho",
        "Urso",
        "Panda"
    )

    private val _mensagem = MutableStateFlow("")
    val mensagem = _mensagem.asStateFlow()

    private val _acertos = MutableStateFlow<List<String>>(emptyList())
    val acertos = _acertos.asStateFlow()

    fun verificarPalavra(entrada: String) {
        val palavra = entrada.trim()

        when {
            // Se já acertou essa palavra antes
            _acertos.value.any { it.equals(palavra, ignoreCase = true) } -> {
                _mensagem.value = "Você já acertou '$palavra'!"
            }

            // Se a palavra existe na lista
            todasPalavras.any { it.equals(palavra, ignoreCase = true) } -> {
                _mensagem.value = "Acertou!"
                _acertos.value = _acertos.value + palavra

                // Se já acertou todas
                if (_acertos.value.size == todasPalavras.size) {
                    _mensagem.value = "🏆 Você venceu!"
                }
            }

            else -> {
                _mensagem.value = "Errou! Tente novamente."
            }
        }
    }

    fun novaRodada() {
        _mensagem.value = ""
        _acertos.value = emptyList()
    }
}
