import DBConnection.users
import auth.AuthRepository
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.count
import org.ktorm.entity.filter

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
    override fun checkCredentials(email: String, password: String): Boolean =
        db.users
            .filter { it.email eq email }
            .filter { it.password eq password }
            .count() > 0
}
