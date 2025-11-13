package com.example.jogodeadvinhar.ui.theme.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jogodeadvinhar.viewmodel.PalavraViewModel

@Composable
fun TelaJogoPalavras(viewModel: PalavraViewModel = viewModel(),
                     navController: NavController) {

    val mensagem by viewModel.mensagem.collectAsState()

    val acertos by viewModel.acertos.collectAsState()

    var entrada by remember { mutableStateOf("") }

    val FundoPrincipal = Color(0xFF7B1FA2)
    val CardFundo = Color(0xFFE1BEE7)
    val BotaoRoxo = Color(0xFF9C27B0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoPrincipal)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Jogo de Adivinhação de Palavras",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Card de referência (fica em branco)
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(120.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardFundo),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Digite o nome de um animal...",
                        color = Color.Black.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = entrada,
                onValueChange = { entrada = it },
                label = { Text("Digite um nome") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.verificarPalavra(entrada)
                    entrada = ""
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BotaoRoxo,
                    contentColor = Color.White
                )
            ) {
                Text("Verificar", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (mensagem.isNotEmpty()) {
                Text(
                    text = mensagem,
                    color = if (mensagem.contains("Acertou")) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Palavras Acertadas:",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                acertos.forEach { palavra ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(45.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = CardFundo)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = palavra,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (mensagem.contains("venceu", ignoreCase = true)) {
                Button(
                    onClick = { viewModel.novaRodada() },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BotaoRoxo,
                        contentColor = Color.White
                    )
                ) {
                    Text("Jogar Novamente", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
