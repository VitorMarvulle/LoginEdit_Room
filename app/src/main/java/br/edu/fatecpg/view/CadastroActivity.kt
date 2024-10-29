package br.edu.fatecpg.view


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import br.edu.fatecpg.R
import br.edu.fatecpg.database.AppDatabase
import br.edu.fatecpg.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastroActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        // Inicializar o banco de dados
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "usuarios"
        ).fallbackToDestructiveMigration().build()

        val btnSave = findViewById<Button>(R.id.btn_Save)
        val edtNome = findViewById<EditText>(R.id.edt_Nome)
        val edtSobrenome = findViewById<EditText>(R.id.edt_Sobrenome)
        val edtEmail = findViewById<EditText>(R.id.edt_Email)
        val edtSenha = findViewById<EditText>(R.id.edt_Senha)

        btnSave.setOnClickListener {
            val nome = edtNome.text.toString()
            val sobrenome = edtSobrenome.text.toString()
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()

            if (nome.isNotEmpty() && sobrenome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
                val usuario = User(nome = nome, sobrenome = sobrenome, email = email, senha = senha)

                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        db.userDao().inserir(usuario) // Salvar no banco
                    }
                    Toast.makeText(this@CadastroActivity, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Fecha a tela de cadastro e retorna à anterior
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}