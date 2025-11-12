package com.example.jogodeadvinhar


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.OutlinedTextField

// Definição de Cores
val corFundoRoxa = Color(0xFF6A1B9A)
val corBotaoLaranja = Color(0xFFF57C00)
val corCartaoBranco = Color.White
val corBordaBotaoClaro = Color(0xFFFFF3E0)

// --- TELA 1: INICIAL (Não muda) ---
@Composable
fun Tela1_Inicial(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundoRoxa)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Cabeçalho
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Troféu",
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = "Olá, Jogador!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Teste seus conhecimentos",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            // Cartão Central
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = corCartaoBranco,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bem-vindo ao Jogo",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(32.dp))

                    Button(
                        onClick = { navController.navigate(Destino.TelaJogar.rota) },
                        colors = ButtonDefaults.buttonColors(containerColor = corFundoRoxa),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text("Jogar Agora", modifier = Modifier.padding(vertical = 8.dp))
                    }
                    Spacer(Modifier.height(16.dp))

                    BotaoMenu(text = "Configurações", icon = Icons.Default.Settings) {
                        navController.navigate(Destino.TelaConfiguracoes.rota)}
                    BotaoMenu(text = "Ranking", icon = Icons.Default.Star) { /* TODO */ }


                    Text(
                        text = "Acesso Restrito",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )

                    Button(
                        onClick = {
                            navController.navigate(Destino.TelaAdmin.rota)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = corBordaBotaoClaro),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, corBotaoLaranja, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = corBotaoLaranja, modifier = Modifier.padding(end = 8.dp))
                        Text(
                            "Acesso Administrador",
                            color = corBotaoLaranja,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            // Rodapé
            Text(
                text = "© 2025 - Divirta-se jogando!",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}


@Composable
fun BotaoMenu(text: String, icon: ImageVector, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
        Text(text, modifier = Modifier.padding(vertical = 8.dp))
        Spacer(Modifier.weight(1f))
    }
    Spacer(Modifier.height(12.dp))
}


// --- TELA 2: LOGIN ADMIN (Versão com Firebase) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela2_LoginAdmin(navController: NavController, viewModel: ViewModelAdmin) {

    // Pega o estado completo (incluindo loading/erro/sucesso)
    val estado by viewModel.estadoUi.collectAsState()

    // ** 1. EFEITO PARA NAVEGAR EM CASO DE SUCESSO **
    LaunchedEffect(estado.sucessoLogin) {
        if (estado.sucessoLogin) {
            // Navega para o painel e limpa a tela de login da pilha
            navController.navigate(Destino.PainelAdmin.rota) {
                popUpTo(Destino.TelaAdmin.rota) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = corFundoRoxa
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = corCartaoBranco,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ... (Ícone e Textos da tela de login) ...
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Cadeado",
                        tint = Color.White,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(50))
                            .background(corBotaoLaranja)
                            .padding(16.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Acesso Administrador",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Entre com suas credenciais",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // ** 2. CAMPOS ATUALIZADOS **
                    OutlinedTextField(
                        value = estado.usuario,
                        onValueChange = { viewModel.aoMudarUsuario(it) },
                        label = { Text("Usuário (E-mail)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        enabled = !estado.estaCarregando, // Desabilita
                        isError = estado.erroLogin != null  // Marca erro
                    )
                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = estado.senha,
                        onValueChange = { viewModel.aoMudarSenha(it) },
                        label = { Text("Senha") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        enabled = !estado.estaCarregando, // Desabilita
                        isError = estado.erroLogin != null  // Marca erro
                    )

                    // ** 3. MOSTRAR MENSAGEM DE ERRO **
                    if (estado.erroLogin != null) {
                        Text(
                            text = estado.erroLogin!!,
                            color = MaterialTheme.colorScheme.error, // Cor de erro padrão
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    } else {
                        Spacer(Modifier.height(24.dp))
                    }

                    // ** 4. BOTÃO COM ESTADO DE LOADING **
                    Button(
                        onClick = { viewModel.tentarLogin() },
                        colors = ButtonDefaults.buttonColors(containerColor = corBotaoLaranja),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !estado.estaCarregando // Desabilita
                    ) {
                        if (estado.estaCarregando) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 3.dp
                            )
                        } else {
                            Text("Entrar", modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                    Spacer(Modifier.height(16.dp))

                    TextButton(onClick = { /* TODO */ }, enabled = !estado.estaCarregando) {
                        Text("Esqueci minha senha", color = Color.Gray)
                    }
                }
            }

            // Rodapé
            Text(
                text = "Esta área é restrita a administradores. Acesso não autorizado é proibido.",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// --- TELA 3: PAINEL ADMIN ---
@Composable
fun TelaAdminPainel(navController: NavController) {
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