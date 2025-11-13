package com.example.jogodeadvinhar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pontuacoes")
data class Pontuacao(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val jogador: String,
    val acertos: Int
)