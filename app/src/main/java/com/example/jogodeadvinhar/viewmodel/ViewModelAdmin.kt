package com.example.jogodeadvinhar.viewmodel // Verifique seu pacote

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

 
data class EstadoUiAdmin(
    val usuario: String = "",
    val senha: String = "",
    val sucessoLogin: Boolean = false,
    val estaCarregando: Boolean = false, // Para mostrar um loading
    val erroLogin: String? = null       // Para mostrar mensagens de erro
)


class ViewModelAdmin : ViewModel() {

    // Instância da autenticação do Firebase
    private val auth: FirebaseAuth = Firebase.auth

    private val _estadoUi = MutableStateFlow(EstadoUiAdmin())
    val estadoUi: StateFlow<EstadoUiAdmin> = _estadoUi.asStateFlow()

    fun aoMudarUsuario(usuario: String) {
        _estadoUi.update { estadoAtual ->
            // Limpa o erro anterior ao digitar
            estadoAtual.copy(usuario = usuario, erroLogin = null)
        }
    }

    fun aoMudarSenha(senha: String) {
        _estadoUi.update { estadoAtual ->
            // Limpa o erro anterior ao digitar
            estadoAtual.copy(senha = senha, erroLogin = null)
        }
    }

  
    fun tentarLogin() {
        val email = estadoUi.value.usuario
        val senha = estadoUi.value.senha

        // 1. Validação básica
        if (email.isBlank() || senha.isBlank()) {
            _estadoUi.update { it.copy(erroLogin = "E-mail e senha não podem estar vazios.") }
            return
        }

        // 2. Ativa o estado de "carregando"
        _estadoUi.update { it.copy(estaCarregando = true) }

        // 3. Chama a API do Firebase
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // SUCESSO!
                    _estadoUi.update {
                        it.copy(
                            sucessoLogin = true,
                            estaCarregando = false,
                            erroLogin = null
                        )
                    }
                } else {
                  
               
           
                    _estadoUi.update {
                        it.copy(
                            sucessoLogin = false,
                            estaCarregando = false,
                            erroLogin = "Falha na autenticação. Verifique suas credenciais."
                        )
                    }
                }
            }
    }
}