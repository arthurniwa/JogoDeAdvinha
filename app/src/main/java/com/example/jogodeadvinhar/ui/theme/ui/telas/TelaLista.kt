package com.example.jogodeadvinhar.ui.theme.ui.telas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jogodeadvinhar.viewmodel.AlterarListaViewModel
import com.example.jogodeadvinhar.viewmodel.AlterarListaViewModelFactory
import com.example.jogodeadvinhar.data.local.AppDatabase
import com.example.jogodeadvinhar.data.repositorio.ListaRepository

@Composable
fun TelaLista() {
    val context = LocalContext.current

    val dao = remember { AppDatabase.getDatabase(context).listaDAO() }
    val repository = remember { ListaRepository(dao) }
    val viewModel: AlterarListaViewModel = viewModel(
        factory = AlterarListaViewModelFactory(repository)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título da Lista
        Text(
            text = "Minhas Tarefas",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF4A148C),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "Gerencie suas tarefas aqui",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6A1B9A),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input e botão
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = uiState.textoInput,
                onValueChange = viewModel::onTextoInputChange,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                placeholder = { Text("Digite uma nova tarefa...") },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { viewModel.onAdicionarOuAtualizar() },
                modifier = Modifier.height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B1FA2))
            ) {
                Text(uiState.textoBotao, color = Color.White)
            }
        }

        // Lista de itens
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.itens) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.nome,
                            color = Color(0xFF4A148C),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Row {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar",
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable { viewModel.onEditar(item) },
                                tint = Color(0xFF1976D2)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Deletar",
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable { viewModel.onDeletar(item) },
                                tint = Color(0xFFD32F2F)
                            )
                        }
                    }
                }
            }
        }
    }
}