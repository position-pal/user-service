import DBConnection.users
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.filter
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.toList
import org.mindrot.jbcrypt.BCrypt
import user.UserRepository

/**
 * Implementation of the UserRepository interface using PostgreSQL as the database.
 */
class PostgresUserRepository(private val db: Database = DBConnection.getDatabaseObject()) : UserRepository {

    /**
     * Saves a user to the database.
     *
     * @param user the user entity to be saved
     * @return the saved user entity with the generated ID
     */
    override fun save(user: User): User {
        val userId = user.userData.id.ifBlank { java.util.UUID.randomUUID().toString() }
        db.insert(Users) {
            set(it.id, userId)
            set(it.name, user.userData.name)
            set(it.surname, user.userData.surname)
            set(it.email, user.userData.email)
            set(it.password, BCrypt.hashpw(user.password, BCrypt.gensalt()))
        }
        return user.copy(userData = user.userData.copy(id = userId))
    }

    /**
     * Finds a user by their ID.
     *
     * @param userId the unique identifier of the user
     * @return the user entity if found, otherwise null
     */
    override fun findById(userId: String): User? = db.users.filter { it.id eq userId }.firstOrNull()

    /**
     * Updates a user in the database.
     *
     * @param user the user entity to be updated
     * @return the updated user entity if the update was successful, otherwise null
     */
    override fun update(user: User): User? {
        val affectedRows = db.update(Users) {
            if (user.userData.name.isNotBlank()) {
                set(it.name, user.userData.name)
            }
            if (user.userData.surname.isNotBlank()) {
                set(it.surname, user.userData.surname)
            }
            if (user.userData.email.isNotBlank()) {
                set(it.email, user.userData.email)
            }
            if (user.password.isNotBlank()) {
                set(it.password, BCrypt.hashpw(user.password, BCrypt.gensalt()))
            }
            where {
                it.id eq user.userData.id
            }
        }
        return if (affectedRows == 1) user else null
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the unique identifier of the user
     * @return true if the user was deleted, otherwise false
     */
    override fun deleteById(userId: String): Boolean {
        val affectedRows = db.delete(Users) { it.id eq userId }
        return affectedRows == 1
    }

    /**
     * Finds all users in the database.
     *
     * @return a list of all user entities
     */
    override fun findAll(): List<User> = db.users.toList()

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user
     * @return the user entity if found, otherwise null
     */
    override fun findByEmail(email: String): User? {
        return db.users.filter { it.email eq email }.firstOrNull()
    }
}
