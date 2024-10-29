package br.edu.fatecpg.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.R
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Exibir dados do usuário na tela
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
    }
}
