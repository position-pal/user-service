import DBConnection.groups
import group.GroupRepository
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.filter
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.toList

/**
 * Implementation of the GroupRepository interface using PostgreSQL as the database.
 */
class PostgresGroupRepository : GroupRepository {

    private val db: Database = DBConnection.getDatabaseObject()

    /**
     * Saves the membership information for a group.
     *
     * @param groupId the unique identifier of the group
     * @param userIds the list of user IDs to be added to the group
     */
    private fun saveMembership(groupId: String, userIds: List<String>) {
        for (userId in userIds) {
            db.insert(Memberships) {
                set(it.groupId, groupId)
                set(it.userId, userId)
            }
        }
    }

    /**
     * Saves a group to the database.
     *
     * @param group the group entity to be saved
     * @return the saved group entity with the generated ID
     */
    override fun save(group: Group): Group {
        val groupId = group.id.ifBlank { java.util.UUID.randomUUID().toString() }
        db.insert(Groups) {
            set(it.id, groupId)
            set(it.name, group.name)
            set(it.createdBy, group.createdBy)
        }
        saveMembership(groupId, group.members)
        return group.copy(id = groupId)
    }

    /**
     * Finds a group by its ID.
     *
     * @param groupId the unique identifier of the group
     * @return the group entity if found, otherwise null
     */
    override fun findById(groupId: String): Group? {
        return db.groups.filter { it.id eq groupId }.firstOrNull()
    }

    /**
     * Updates a group in the database.
     *
     * @param group the group entity to be updated
     * @return the updated group entity if the update was successful, otherwise null
     */
    override fun update(group: Group): Group? {
        val affectedRows = db.update(Groups) {
            set(it.name, group.name)
            set(it.createdBy, group.createdBy)
            where { it.id eq group.id }
        }
        // todo update memberships
        return if (affectedRows == 1) group else null
    }

    /**
     * Deletes a group by its ID.
     *
     * @param groupId the unique identifier of the group
     * @return true if the group was deleted, otherwise false
     */
    override fun deleteById(groupId: String): Boolean {
        val affectedRows = db.delete(Groups) { it.id eq groupId }
        return affectedRows == 1
    }

    /**
     * Finds all groups in the database.
     *
     * @return a list of all group entities
     */
    override fun findAll(): List<Group> {
        return db.groups.toList()
    }

    /**
     * Adds a member to a group.
     *
     * @param groupId the unique identifier of the group
     * @param userId the unique identifier of the user to be added
     * @return the updated group entity if the member was added, otherwise null
     */
    override fun addMember(groupId: String, userId: String): Group? {
        db.insert(Memberships) {
            set(it.groupId, groupId)
            set(it.userId, userId)
        }
        return findById(groupId)
    }

    /**
     * Removes a member from a group.
     *
     * @param groupId the unique identifier of the group
     * @param userId the unique identifier of the user to be removed
     * @return the updated group entity if the member was removed, otherwise null
     */
    override fun removeMember(groupId: String, userId: String): Group? {
        db.delete(Memberships) {
            it.groupId eq groupId
            it.userId eq userId
        }
        return findById(groupId)
    }
}
