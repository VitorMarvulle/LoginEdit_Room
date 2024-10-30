package br.edu.fatecpg.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid:Long = 0,
    var nome: String,
    var sobrenome: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "senha")
    var senha: String
)
