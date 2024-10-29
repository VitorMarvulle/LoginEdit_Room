//package br.edu.fatecpg.view
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import androidx.room.Room
//import br.edu.fatecpg.R
//import br.edu.fatecpg.database.AppDatabase
//import br.edu.fatecpg.model.User
//import kotlinx.coroutines.launch
//
//class EdicaoActivity : AppCompatActivity() {
//
//    private lateinit var edtNome: EditText
//    private lateinit var edtSobrenome: EditText
//    private lateinit var edtEmail: EditText
//    private lateinit var edtSenha: EditText
//    private lateinit var btnSaveEdit: Button
//    private lateinit var db: AppDatabase
//    private var userEmail: String? = null // Email original do usuário para identificação no banco
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edicao)
//
//        // Inicializar o banco de dados
//        db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "usuarios"
//        ).build()
//
//        // Inicializar as views
//        edtNome = findViewById(R.id.edt_NomeEdit)
//        edtSobrenome = findViewById(R.id.edt_SobrenomeEdit)
//        edtEmail = findViewById(R.id.edt_EmailEdit)
//        edtSenha = findViewById(R.id.edt_SenhaEdit)
//        btnSaveEdit = findViewById(R.id.btn_SaveEdit)
//
//        // Receber os dados do usuário atual
//        userEmail = intent.getStringExtra("email")
//        edtNome.setText(intent.getStringExtra("nome"))
//        edtSobrenome.setText(intent.getStringExtra("sobrenome"))
//        edtEmail.setText(userEmail)
//
//        // Salvar as alterações ao clicar no botão
//        btnSaveEdit.setOnClickListener {
//            val nome = edtNome.text.toString()
//            val sobrenome = edtSobrenome.text.toString()
//            val email = edtEmail.text.toString()
//            val senha = edtSenha.text.toString()
//
//            if (nome.isNotEmpty() && sobrenome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
//                val updatedUser = User(nome = nome, sobrenome = sobrenome, email = email, senha = senha)
//
//                lifecycleScope.launch {
//                    userEmail?.let {
//                        db.userDao().updateUser(updatedUser)
//                        Toast.makeText(this@EdicaoActivity, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show()
//                        finish() // Fecha a tela de edição
//                    }
//                }
//            } else {
//                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}
