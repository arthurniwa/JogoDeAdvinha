package com.example.jogodeadvinhar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Define todas as rotas/telas do app.
 */
sealed class Destino(val rota: String) {
    object TelaInicial : Destino("tela_inicial")
    object TelaAdmin : Destino("tela_admin")
    object PainelAdmin : Destino("tela_admin_painel")
    // object Ranking : Destino("ranking")
    object TelaConfiguracoes : Destino("tela_configuracoes")
}

/**
 * Controla a navegação entre as telas (AULA 13-14: NAVIGATION)
 */
@Composable
fun NavegacaoApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destino.TelaInicial.rota) {

        composable(Destino.TelaInicial.rota) {
            Tela1_Inicial(navController = navController)
        }

        composable(Destino.TelaAdmin.rota) {
            // Cria o ViewModel que será passado para a tela de login
            val viewModelAdmin: ViewModelAdmin = viewModel()
            Tela2_LoginAdmin(
                navController = navController,
                viewModel = viewModelAdmin
            )
        }
        composable(Destino.TelaConfiguracoes.rota) {
            TelaConfiguracoes(navController = navController)
        }

        composable(Destino.PainelAdmin.rota) {
            TelaAdminPainel(navController = navController)
        }
    }
}