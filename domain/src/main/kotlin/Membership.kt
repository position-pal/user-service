import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.BaseTable
import org.ktorm.schema.varchar

/**
 * Data class representing a Membership entity.
 *
 * @property userId the unique identifier of the user
 * @property groupId the unique identifier of the group
 */
data class Membership(
    val userId: String,
    val groupId: String,
)

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
