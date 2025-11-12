package com.example.jogodeadvinhar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perfil_tabela")
data class Nome(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    var nome: String
)