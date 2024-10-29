package br.edu.fatecpg.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.fatecpg.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun inserir(usuarios: User)

    @Query("SELECT * FROM usuarios") // Verifique se o nome da tabela está correto
    suspend fun getAll(): List<User> // Adicione `suspend` para usar com corrotinas

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha")
    suspend fun autenticar(email: String, senha: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Query("DELETE FROM usuarios WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)
}
