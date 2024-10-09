import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.BaseTable
import org.ktorm.schema.varchar

/**
 * Data class representing a Group entity.
 *
 * @property id the unique identifier of the group
 * @property name the name of the group
 * @property members the list of usersId who are members of the group
 * @property createdBy the userId of user who created the group
 */
data class Group(
    val id: String,
    val name: String,
    val members: List<User>,
    val createdBy: User,
)

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
        members = emptyList(), // todo implement this logic
        createdBy = User(row[createdBy].orEmpty(), "", "", "", "", ""), // todo implement this logic
    )
}
