package br.edu.fatecpg.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid:Long = 0,
    val nome: String,
    val sobrenome: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "senha")
    val senha: String
)
