package com.example.jogodeadvinhar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
fun TelaConfiguracoes(navController: NavController) {


    val musicaFundo = true
    val efeitosSonoros = true
    val notificacoes = true
    val temaEscuro = false
    val idioma = "PT"


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Voltar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Botão de voltar
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = corFundoRoxa,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = corFundoRoxa
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cartão Branco Principal
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxWidth()
                ) {
                    // Títulos
                    Text(
                        text = "Configurações",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Personalize sua experiência",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(24.dp))

                    // Seção 1: Som e Áudio
                    ConfigSectionTitle(icon = Icons.Default.Phone, title = "Som e Áudio")
                    ConfigRowSwitch(
                        title = "Música de Fundo",
                        checked = musicaFundo,
                        onCheckedChange = {} // Não faz nada
                    )
                    ConfigRowSwitch(
                        title = "Efeitos Sonoros",
                        checked = efeitosSonoros,
                        onCheckedChange = {} // Não faz nada
                    )
                    Spacer(Modifier.height(24.dp))

                    // Seção 2: Notificações
                    ConfigSectionTitle(icon = Icons.Default.Notifications, title = "Notificações")
                    ConfigRowSwitch(
                        title = "Ativar Notificações",
                        checked = notificacoes,
                        onCheckedChange = {} // Não faz nada
                    )
                    Spacer(Modifier.height(24.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Coluna Tema
                        Column(modifier = Modifier.weight(1f)) {
                            ConfigSectionTitle(icon = Icons.Default.Close, title = "Tema")
                            Switch(
                                checked = temaEscuro,
                                onCheckedChange = {}, // Não faz nada
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            ConfigSectionTitle(icon = Icons.Default.LocationOn, title = "Idioma")

                            OutlinedTextField(
                                value = idioma,
                                onValueChange = {}, // Não faz nada
                                readOnly = true,
                                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    // Botão Salvar
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = corFundoRoxa),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Salvar Alterações", modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ConfigSectionTitle(icon: ImageVector, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.Gray)
        Spacer(Modifier.width(8.dp))
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
    }
    Spacer(Modifier.height(12.dp))
}


@Composable
fun ConfigRowSwitch(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontSize = 16.sp, color = Color.DarkGray)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
    Spacer(Modifier.height(8.dp))
}