import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.BaseTable
import org.ktorm.schema.varchar

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

/**
 * Object representing the Groups table in the database.
 *
 * @property id the unique identifier of the group
 * @property name the name of the group
 * @property createdBy the userId of user who created the group
 */
object Groups : BaseTable<Group>("groups") {
    val id = varchar("id").primaryKey()
    val name = varchar("name")
    val createdBy = varchar("created_by")

    /**
     * Creates a Group entity from a database row.
     *
     * @param row the database row
     * @param withReferences whether to include references
     * @return the Group entity
     */
    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = Group(
        id = row[id].orEmpty(),
        name = row[name].orEmpty(),
        members = emptyList(),
        createdBy = User(id = row[createdBy].orEmpty(), name = "", surname = "", email = "", password = "", role = ""),
    )
}

/**
 * Object representing the Memberships table in the database.
 *
 * @property userId the unique identifier of the user
 * @property groupId the unique identifier of the group
 */
object Memberships : BaseTable<Membership>("membership") {
    val userId = varchar("user_id").primaryKey() // Column for user ID
    val groupId = varchar("group_id").primaryKey() // Column for group ID

    /**
     * Creates a Membership entity from a database row.
     *
     * @param row the database row
     * @param withReferences whether to include references
     * @return the Membership entity
     */
    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = Membership(
        userId = row[userId].orEmpty(),
        groupId = row[groupId].orEmpty(),
    )
}
