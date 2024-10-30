package br.edu.fatecpg.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import br.edu.fatecpg.R
import br.edu.fatecpg.database.AppDatabase
import br.edu.fatecpg.model.User
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnCadastrar: Button
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "usuarios"
        ).build()

        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)
        btnLogin = findViewById(R.id.btn_login)
        btnCadastrar = findViewById(R.id.btn_cadastro)

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                loginUser(email, senha)
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, senha: String) {
        lifecycleScope.launch {
            val user: User? = db.userDao().autenticar(email, senha)

            if (user != null) {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                intent.putExtra("nome", user.nome)
                intent.putExtra("sobrenome", user.sobrenome)
                intent.putExtra("email", user.email)
                intent.putExtra("senha", user.senha)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@MainActivity, "Email ou senha incorretos!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

