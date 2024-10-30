package br.edu.fatecpg.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.R
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import br.edu.fatecpg.database.AppDatabase
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var btn_voltar: Button
    private lateinit var btn_deletar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "usuarios"
        ).build()


        val nome = intent.getStringExtra("nome")
        val sobrenome = intent.getStringExtra("sobrenome")
        val email = intent.getStringExtra("email")
        val senha = intent.getStringExtra("senha")

        val nomeTextView: TextView = findViewById(R.id.pf_nome)
        val sobrenomeTextView: TextView = findViewById(R.id.pf_sobrenome)
        val emailTextView: TextView = findViewById(R.id.pf_email)
        val senhaTextView: TextView = findViewById(R.id.pf_senha)

        nomeTextView.text = nome
        sobrenomeTextView.text = sobrenome
        emailTextView.text = email
        senhaTextView.text = senha


        val btn_editar: Button = findViewById(R.id.btn_editar)
        btn_editar.setOnClickListener {
            val intent = Intent(this, EdicaoActivity::class.java)
            intent.putExtra("nome", nome)
            intent.putExtra("sobrenome", sobrenome)
            intent.putExtra("email", email)
            intent.putExtra("senha", senha)
            startActivity(intent)
        }


        btn_voltar = findViewById(R.id.btn_voltar)
        btn_voltar.setOnClickListener {
            val intent = Intent(this@ProfileActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_deletar = findViewById(R.id.btn_deletar)
        btn_deletar.setOnClickListener {
            deletarConta(email)
        }
    }

    private fun deletarConta(email: String?) {
        lifecycleScope.launch {
            email?.let { email ->
                val usuario = db.userDao().findUserByEmail(email)
                usuario?.let {
                    db.userDao().deleteUser(it)
                    Toast.makeText(
                        this@ProfileActivity,
                        "Conta deletada com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Redireciona para a tela de login após excluir
                    val intent = Intent(this@ProfileActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } ?: run {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Usuário não encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
