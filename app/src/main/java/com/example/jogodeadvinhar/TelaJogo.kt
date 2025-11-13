package com.example.jogodeadvinhar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun TelaJogo(modifier: Modifier = Modifier,
             navController: NavController) {
    val context = LocalContext.current.applicationContext
    val repository = remember {
        val dao = AppDatabase.getDatabase(context).pontuacaoDAO()
        PontuacaoRepository(dao)
    }
    val factory = remember { JogoViewModelFactory(repository) }
    val viewModel: JogoViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var mensagem by remember { mutableStateOf("") }

    val FundoPrincipalApp = Color(0xFF7B1FA2)
    val SuperficieCard = Color(0xFFE1BEE7)
    val IconeVerde = Color(0xFF69F0AE)
    val BotaoRoxo = Color(0xFF9C27B0)
    val CardOculto = Color(0xFF9575CD)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FundoPrincipalApp)
    ) {
        // === BOTÃO SAIR ===
        TextButton(
            onClick = { navController.navigate("tela_jogar") },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text(
                text = "Sair",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Títulos  ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Jogo de Adivinhação",
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Adivinhação de Imagem",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Maior Pontuação: ${uiState.maiorPontuacao} Acertos",
                    color = IconeVerde,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 6.dp)
                )

                Text(
                    "| Tentativas: ${uiState.tentativas}",
                    color = Color.White.copy(alpha = 0.9f),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Card de Referência  ---
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(220.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SuperficieCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Ícone de referência",
                                tint = IconeVerde,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Encontre essa imagem:",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black.copy(alpha = 0.8f),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Card(
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            modifier = Modifier
                                .size(140.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.gato_diferente),
                                contentDescription = "Imagem de referência",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                                alignment = Alignment.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Card do Jogo ---
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.89f)
                    .weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SuperficieCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Escolha a carta correta:",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black.copy(alpha = 0.8f),
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            uiState.imagens.take(3).forEach { imagem ->
                                GameCard(
                                    imagem = imagem,
                                    hiddenCardColor = CardOculto
                                ) {
                                    if (!uiState.jogoFinalizado && !imagem.revelada) {
                                        viewModel.revelarImagem(imagem.id)
                                        mensagem =
                                            if (imagem.diferente) "Acertou!" else "Tente de novo!"
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            uiState.imagens.drop(3).forEach { imagem ->
                                GameCard(
                                    imagem = imagem,
                                    hiddenCardColor = CardOculto
                                ) {
                                    if (!uiState.jogoFinalizado && !imagem.revelada) {
                                        viewModel.revelarImagem(imagem.id)
                                        mensagem =
                                            if (imagem.diferente) "Acertou!" else "Tente de novo!"
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .height(28.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (mensagem.isNotEmpty()) {
                            Text(
                                text = mensagem,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (mensagem.contains("Acertou")) Color(0xFF4CAF50)
                                else Color(0xFFF44336),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.embaralhar()
                    mensagem = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BotaoRoxo,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text("EMBARALHAR", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


// ---
@Composable
fun GameCard(imagem: Imagem, hiddenCardColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(80.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (!imagem.revelada) hiddenCardColor else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            if (imagem.revelada) {
                val painter = if (imagem.diferente)
                    painterResource(id = R.drawable.gato_diferente)
                else
                    painterResource(id = R.drawable.gato_igual)

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Transparent)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(4.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                    )
                }
            }
        }
    }
}
