package com.example.jogodeadvinhar.ui.theme.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jogodeadvinhar.Destino
import com.example.jogodeadvinhar.viewmodel.NomeUiState
import com.example.jogodeadvinhar.viewmodel.NomeViewModel
import com.example.jogodeadvinhar.viewmodel.NomeViewModelFactory
import com.example.jogodeadvinhar.data.local.AppDatabase
import com.example.jogodeadvinhar.data.repositorio.NomeRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// ---------------------------
@Composable
fun PainelAdm(navController: NavController) {
    val context = LocalContext.current

    // --- INICIALIZAÇÃO DO VIEWMODEL ---
    val nomeDao = remember { AppDatabase.getDatabase(context).nomeDAO() }
    val viewModel: NomeViewModel = viewModel(factory = NomeViewModelFactory(NomeRepository(nomeDao)))
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val TextColor = Color.White
    val BackgroundColor = Color(0xFF7B1FA2)
    val LightCardSurface = Color(0xFFE1BEE7)
    val IconGreen = Color(0xFF69F0AE)
    val SecondaryCardDark = Color(0xFF9C27B0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Top Bar (Cabeçalho do Painel) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Configurações",
                    tint = TextColor,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Painel", style = MaterialTheme.typography.headlineSmall, color = TextColor, fontWeight = FontWeight.Bold)
                    Text("Administrativo", style = MaterialTheme.typography.bodySmall, color = TextColor.copy(alpha = 0.8f))
                }
            }
            Text(
                "Sair",
                color = TextColor,
                modifier = Modifier.clickable {
                    Firebase.auth.signOut()
                    navController.navigate(Destino.TelaInicial.rota) {
                        popUpTo(0)
                    }
                }
            )
        }

        // --- 1. BLOCO DO PERFIL ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 110.dp, max = 150.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = LightCardSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            BlocoPerfil(uiState = uiState, viewModel = viewModel)

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Login com Sucesso!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Bem-vindo, Admin!")
                    Spacer(Modifier.height(20.dp))
                    Button(onClick = {
                        // ** Ação de Logout do Firebase **
                        Firebase.auth.signOut()

                        // Navega de volta para a tela inicial e limpa tudo
                        navController.navigate(Destino.TelaInicial.rota) {
                            popUpTo(0) // Limpa toda a pilha de navegação
                        }
                    }) {
                        Text("Sair")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 240.dp, max = 340.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = LightCardSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            TelaLista()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. BLOCO DE AÇÕES RÁPIDAS ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Text(
                "Ações Rápidas",
                style = MaterialTheme.typography.titleMedium,
                color = TextColor.copy(alpha = 0.9f),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Linha 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardAcao(texto = "Gerenciar Perguntas", icone = Icons.Outlined.Info)
                CardAcao(texto = "Gerenciar Usuários", icone = Icons.Outlined.Person)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

// ---------------------------
// BLOCO PERFIL
// ---------------------------
@Composable
fun BlocoPerfil(uiState: NomeUiState, viewModel: NomeViewModel) {
    val IconGreen = Color(0xFF69F0AE)
    val SecondaryCardDark = Color(0xFF9C27B0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                if (uiState.modoEdicao) {
                    TextField(
                        value = uiState.nome,
                        onValueChange = viewModel::onNomeChange,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        textStyle = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(
                            onClick = viewModel::salvarNome,
                            colors = ButtonDefaults.buttonColors(containerColor = IconGreen)
                        ) {
                            Text("Salvar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cancelar Edição",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(onClick = viewModel::alternarModoEdicao)
                                .align(Alignment.CenterVertically)
                        )
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = uiState.nome,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar Nome",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(onClick = viewModel::alternarModoEdicao)
                        )
                    }
                    Text(
                        text = "Usuário Administrador",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }
            }

            Icon(
                Icons.Outlined.Person,
                contentDescription = "Conta",
                tint = SecondaryCardDark,
                modifier = Modifier.size(80.dp)
            )
        }

        if (!uiState.modoEdicao) {
            Row {
                Tag("Admin", SecondaryCardDark)
                Spacer(modifier = Modifier.width(8.dp))
                Tag(uiState.status, SecondaryCardDark)
            }
        }
    }
}

// ---------------------------
// TAG
// ---------------------------
@Composable
fun Tag(text: String, tagColor: Color) {
    Box(
        modifier = Modifier
            .background(tagColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}

// ---------------------------
// CARD DE AÇÃO
// ---------------------------
@Composable
fun CardAcao(texto: String, icone: ImageVector) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(110.dp)
            .clickable { /* Ação */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icone,
                contentDescription = texto,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFFFFC107)
            )
            Text(
                text = texto,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
