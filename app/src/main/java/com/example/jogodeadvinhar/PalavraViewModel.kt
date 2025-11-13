package com.example.jogodeadvinhar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PalavraViewModel : ViewModel() {

    private val palavras = listOf("Vinicius", "Gustavo", "Arthur", "Eduardo")

    private var indiceAtual = 0

    private val _palavraCorreta = MutableStateFlow(palavras[indiceAtual])
    val palavraCorreta = _palavraCorreta.asStateFlow()

    private val _mensagem = MutableStateFlow("")
    val mensagem = _mensagem.asStateFlow()

    private val _acertos = MutableStateFlow<List<String>>(emptyList())
    val acertos = _acertos.asStateFlow()

    fun verificarPalavra(entrada: String) {
        if (entrada.equals(_palavraCorreta.value, ignoreCase = true)) {
            _mensagem.value = "Acertou!"
            _acertos.value = _acertos.value + _palavraCorreta.value

            if (indiceAtual < palavras.lastIndex) {
                indiceAtual++
                _palavraCorreta.value = palavras[indiceAtual]
            } else {
                _mensagem.value = "🏆 Você venceu!"
            }
        } else {
            _mensagem.value = "Errou! Tente novamente."
        }
    }

    fun novaRodada() {
        indiceAtual = 0
        _palavraCorreta.value = palavras[indiceAtual]
        _mensagem.value = ""
        _acertos.value = emptyList()
    }
}
