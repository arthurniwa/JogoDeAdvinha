package com.example.jogodeadvinhar


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaJogar(navController: NavController) {


    val modoSelecionado = "imagem"
    val nivel = "15+"
    val tempo = "5 min"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Olá, Jogador",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            "Teste",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Destino.TelaConfiguracoes.rota) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Configurações")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            // Barra de navegação inferior como na imagem
            BottomAppBar(
                containerColor = Color.White,
                contentColor = corFundoRoxa,
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = { navController.popBackStack() }) { // Volta para a Tela1 (Home)
                        Icon(Icons.Default.Home, contentDescription = "Início")
                    }
                    IconButton(onClick = { /* TODO: Navegar para Ranking */ }) {
                        Icon(Icons.Default.Star, contentDescription = "Ranking")
                    }
                    IconButton(onClick = { navController.navigate(Destino.TelaConfiguracoes.rota) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Configurações")
                    }
                }
            }
        },
        containerColor = corFundoRoxa
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Cartão 1: Escolha um modo
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        "Escolha um modo",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(16.dp))

                    // Opção 1: Imagem
                    ModoJogoItem(
                        text = "Adivinhação de Imagem",
                        icon = Icons.Default.AccountBox,
                        isSelected = modoSelecionado == "imagem"
                    )
                    Spacer(Modifier.height(12.dp))

                    // Opção 2: Palavra
                    ModoJogoItem(
                        text = "Adivinhação de Palavra",
                        icon = Icons.Default.Edit, // Ícone para texto
                        isSelected = modoSelecionado == "palavra"
                    )
                }
            }

            // Cartões 2 e 3: Nível e Tempo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Cartão 2: Nível
                InfoCard(
                    icon = Icons.Default.KeyboardArrowUp, // Ícone de Nível
                    title = "Nível",
                    value = nivel,
                    modifier = Modifier.weight(1f)
                )

                // Cartão 3: Tempo Médio
                InfoCard(
                    icon = Icons.Default.DateRange, // Ícone de Tempo
                    title = "Tempo Médio",
                    value = tempo,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.weight(1f)) // Empurra o botão "Começar" para baixo

            // Botão Começar
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = corBotaoLaranja),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("COMEÇAR", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ModoJogoItem(text: String, icon: ImageVector, isSelected: Boolean) {
    val borderColor = if (isSelected) Color(0xFF4CAF50) else Color.LightGray // Verde se selecionado
    val contentColor = if (isSelected) Color.Black else Color.Gray
    val iconColor = if (isSelected) borderColor else Color.Gray

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent,
        border = BorderStroke(2.dp, borderColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
                    .size(40.dp)
                    .background(iconColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = contentColor,
                modifier = Modifier.weight(1f)
            )
            RadioButton(
                selected = isSelected,
                onClick = { /* Não faz nada */ },
                colors = RadioButtonDefaults.colors(selectedColor = borderColor)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCard(
    icon: ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        modifier = modifier,
        shadowElevation = 8.dp
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color.Gray)
                Spacer(Modifier.width(8.dp))
                Text(title, fontSize = 14.sp, color = Color.Gray)
            }
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}