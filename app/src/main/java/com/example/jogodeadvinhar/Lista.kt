package com.example.jogodeadvinhar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lista")
data class Lista(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String
)