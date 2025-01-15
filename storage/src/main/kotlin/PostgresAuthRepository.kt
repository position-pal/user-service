import DBConnection.users
import auth.AuthRepository
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.firstOrNull
import org.mindrot.jbcrypt.BCrypt

/**
 * Implementation of the AuthRepository interface for PostgreSQL.
 *
 * @property db The database connection object.
 */
class PostgresAuthRepository(private val db: Database = DBConnection.getDatabaseObject()) : AuthRepository {

    /**
     * Checks the credentials of a user.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return `true` if the credentials are valid, `false` otherwise.
     */
    override fun checkCredentials(email: String, password: String): Boolean {
        val user = db.users
            .filter { it.email eq email }
            .firstOrNull()

        return user != null && BCrypt.checkpw(password, user.password)
    }
}
