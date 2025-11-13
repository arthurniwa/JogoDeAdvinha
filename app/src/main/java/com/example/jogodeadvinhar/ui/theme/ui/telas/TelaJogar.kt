package com.example.jogodeadvinhar.ui.theme.ui.telas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jogodeadvinhar.Destino

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaJogar(navController: NavController) {

    var modoSelecionado by remember {mutableStateOf("imagem")}
    var nivel by remember {mutableStateOf("15+")}
    var tempo by remember {mutableStateOf("5 min")}

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
                    IconButton(onClick = { navController.navigate("tela_inicial") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
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
            BottomAppBar(
                containerColor = Color.White,
                contentColor = corFundoRoxa,
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Home, contentDescription = "Início")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Star, contentDescription = "Star")
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

                    ModoJogoItem(
                        text = "Adivinhação de Imagem",
                        icon = Icons.Default.AccountBox,
                        isSelected = modoSelecionado == "imagem",
                        onClick = { modoSelecionado = "imagem"}
                    )
                    Spacer(Modifier.height(12.dp))

                    ModoJogoItem(
                        text = "Adivinhação de Palavra",
                        icon = Icons.Default.Edit,
                        isSelected = modoSelecionado == "palavra",
                        onClick = { modoSelecionado = "palavra"}
                    )

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (modoSelecionado == "imagem") {
                                navController.navigate(Destino.TelaJogo.rota)
                            } else if (modoSelecionado == "palavra") {
                                navController.navigate(Destino.TelaJogoPalavras.rota)
                            }
                        },
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

            // Cartões 2 e 3: Nível e Tempo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoCard(
                    icon = Icons.Default.KeyboardArrowUp,
                    title = "Nível",
                    value = nivel,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        nivel = when (nivel) {
                            "15+" -> "10"
                            "10" -> "5"
                            else -> "15+"
                        }
                    }
                )

                InfoCard(
                    icon = Icons.Default.DateRange,
                    title = "Tempo Médio",
                    value = tempo,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        tempo = when(tempo){
                            "5 min" -> "10 min"
                            "10 min" -> "15 min"
                            else -> "5"
                        }
                    }
                )
            }
            Spacer(Modifier.weight(1f))

            Button(
                onClick = { /*TODO*/},
                colors = ButtonDefaults.buttonColors(containerColor = corBotaoLaranja),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("CONTINUAR", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModoJogoItem(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFF4CAF50) else Color.LightGray
    val contentColor = if (isSelected) Color.Black else Color.Gray
    val iconColor = if (isSelected) borderColor else Color.Gray

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent,
        border = BorderStroke(2.dp, borderColor),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
                onClick = onClick,
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
    modifier: Modifier = Modifier,
    onClick: () -> Unit
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

            Box(modifier = Modifier.clickable(onClick = onClick)) {
                OutlinedTextField(
                    value = value,
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

    }
}
