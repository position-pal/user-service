import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.BaseTable
import org.ktorm.schema.varchar

/**
 * Data class representing a User entity.
 *
 * @property id the unique identifier of the user
 * @property name the first name of the user
 * @property surname the last name of the user
 * @property email the email address of the user
 * @property password the password of the user
 * @property role the role of the user in the system
 */
data class User(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: String,
)

/**
 * Object representing the Users table in the database.
 *
 * @property id the unique identifier of the user
 * @property name the first name of the user
 * @property surname the last name of the user
 * @property email the email address of the user
 * @property password the password of the user
 * @property role the role of the user in the system
 */
object Users : BaseTable<User>("users") {
    val id = varchar("id").primaryKey()
    val name = varchar("name")
    val surname = varchar("surname")
    val email = varchar("email")
    val password = varchar("password")
    val role = varchar("role")

    /**
     * Creates a User entity from a database row.
     *
     * @param row the database row
     * @param withReferences whether to include references
     * @return the User entity
     */
    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = User(
        id = row[id].orEmpty(),
        name = row[name].orEmpty(),
        surname = row[surname].orEmpty(),
        email = row[email].orEmpty(),
        password = row[password].orEmpty(),
        role = row[role].orEmpty(),
    )
}
