package br.edu.fatecpg.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import br.edu.fatecpg.R
import br.edu.fatecpg.database.AppDatabase
import kotlinx.coroutines.launch

class EdicaoActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var edtNome: EditText
    private lateinit var edtSobrenome: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnVoltar: Button
    private lateinit var btnSelecionaImagem: Button
    private lateinit var profilePhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edicao)

        edtNome = findViewById(R.id.edt_NomeEdit)
        edtSobrenome = findViewById(R.id.edt_SobrenomeEdit)
        edtEmail = findViewById(R.id.edt_EmailEdit)
        edtSenha = findViewById(R.id.edt_SenhaEdit)


        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "usuarios"
        ).build()

        val nome = intent.getStringExtra("nome")
        val sobrenome = intent.getStringExtra("sobrenome")
        val email = intent.getStringExtra("email")
        val senha = intent.getStringExtra("senha")

        edtNome = findViewById(R.id.edt_NomeEdit)
        edtSobrenome = findViewById(R.id.edt_SobrenomeEdit)
        edtEmail = findViewById(R.id.edt_EmailEdit)
        edtSenha = findViewById(R.id.edt_SenhaEdit)
        btnSalvar = findViewById(R.id.btn_SaveEdit)
        btnVoltar = findViewById(R.id.btn_voltar)
        btnSelecionaImagem = findViewById(R.id.btn_SelecionaImagem)
        profilePhoto = findViewById(R.id.profile_photo)

        // Preenche os campos EditText
        edtNome.setText(nome)
        edtSobrenome.setText(sobrenome)
        edtEmail.setText(email)
        edtSenha.setText(senha)

        btnSalvar.setOnClickListener {
            salvarAlteracoes()
        }
    }

    private fun salvarAlteracoes() {
        val nome = edtNome.text.toString()
        val sobrenome = edtSobrenome.text.toString()
        val email = edtEmail.text.toString()
        val senha = edtSenha.text.toString()

        if (nome.isNotBlank() && sobrenome.isNotBlank() && email.isNotBlank() && senha.isNotBlank()) {
            lifecycleScope.launch {
                // Carregar o usuário atual do banco de dados
                val usuarioAtual = db.userDao().autenticar(intent.getStringExtra("email") ?: "", intent.getStringExtra("senha") ?: "")
                usuarioAtual?.let { usuario ->
                    // Atualizar as propriedades do objeto existente
                    usuario.nome = nome
                    usuario.sobrenome = sobrenome
                    usuario.email = email
                    usuario.senha = senha

                    // Atualiza o USER no banco de dados
                    db.userDao().updateUser(usuario)
                    Toast.makeText(this@EdicaoActivity, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()

                    // Retorna à tela do MainActivity após salvar as alterações
                    val intent = Intent(this@EdicaoActivity, ProfileActivity::class.java)
                    intent.putExtra("nome", nome)
                    intent.putExtra("sobrenome", sobrenome)
                    intent.putExtra("email", email)
                    intent.putExtra("senha", senha)

                    startActivity(intent)
                    finish()
                } ?: run {
                    Toast.makeText(this@EdicaoActivity, "Erro ao carregar o usuário.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }

}
