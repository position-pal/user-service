import DBConnection.users
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.filter
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.toList
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
        val userId = user.id.ifBlank { java.util.UUID.randomUUID().toString() }
        db.insert(Users) {
            set(it.id, userId)
            set(it.name, user.name)
            set(it.surname, user.surname)
            set(it.email, user.email)
            set(it.password, user.password)
            set(it.role, user.role)
        }
        return user.copy(id = userId)
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
            set(it.name, user.name)
            set(it.surname, user.surname)
            set(it.email, user.email)
            set(it.password, user.password)
            set(it.role, user.role)
            where {
                it.id eq user.id
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
}
