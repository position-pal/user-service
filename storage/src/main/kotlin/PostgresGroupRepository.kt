import DBConnection.groups
import DBConnection.users
import group.GroupRepository
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.inList
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import org.ktorm.entity.filter
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.toList

/**
 * Implementation of the GroupRepository interface using PostgreSQL as the database.
 */
class PostgresGroupRepository(private val db: Database = DBConnection.getDatabaseObject()) : GroupRepository {

    /**
     * Saves the membership information for a group.
     *
     * @param groupId the unique identifier of the group
     * @param users the list of user to be added to the group
     */
    private fun saveMembership(groupId: String, users: List<UserData>) {
        for (user in users) {
            db.insert(Memberships) {
                set(it.groupId, groupId)
                set(it.userId, user.id)
            }
        }
    }

    /**
     * Gets the members of a group.
     *
     * @param groupId the unique identifier of the group
     * @return a list of user entities that are members of the group
     */
    private fun getMembers(groupId: String): List<UserData> =
        db.users.filter {
            it.id inList db.from(Memberships).select(Memberships.userId).where {
                Memberships.groupId eq groupId
            }
        }.toList().map { it.userData }

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
            set(it.createdBy, group.createdBy.id)
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
        val group = db.groups.filter { it.id eq groupId }.firstOrNull() ?: return null
        val members = getMembers(groupId)
        val createdBy = db.users.filter { it.id eq group.createdBy.id }
            .firstOrNull() ?: throw IllegalArgumentException("CreatedBy user not found")
        return group.copy(members = members, createdBy = createdBy.userData)
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
            where { it.id eq group.id }
        }
        return if (affectedRows == 1) findById(group.id) else null
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
        val groups = db.groups.toList()
        return groups.map { group ->
            val members = getMembers(group.id)
            group.copy(members = members)
                .copy(
                    createdBy = db.users.filter { it.id eq group.createdBy.id }.firstOrNull()?.userData
                        ?: return emptyList(),
                )
        }
    }

    /**
     * Adds a member to a group.
     *
     * @param groupId the unique identifier of the group
     * @param user the user to be added
     * @return the updated group entity if the member was added, otherwise null
     */
    override fun addMember(groupId: String, userData: UserData): Group? {
        return runCatching {
            db.insert(Memberships) {
                set(it.groupId, groupId)
                set(it.userId, userData.id)
            }
        }.onFailure { e ->
            println("Error adding member: ${e.message}")
        }.getOrNull()?.let { findById(groupId) }
    }

    /**
     * Removes a member from a group.
     *
     * @param groupId the unique identifier of the group
     * @param user the user to be removed
     * @return the updated group entity if the member was removed, otherwise null
     */
    override fun removeMember(groupId: String, userData: UserData): Group? {
        return runCatching {
            db.delete(Memberships) {
                it.groupId eq groupId
                it.userId eq userData.id
            }
        }.onFailure { e ->
            println("Error removing member: ${e.message}")
        }.getOrNull()?.let { findById(groupId) }
    }

    /**
     * Finds all groups that a user is a member of.
     *
     * @param email the identifier of the user
     * @return a list of group entities that the user is a member of
     */
    override fun findGroupsByUserEmail(email: String): List<Group> {
        val user = db.users.filter { it.email eq email }.firstOrNull() ?: return emptyList()
        return findGroupsByUserId(user.userData.id)
    }

    /**
     * Finds all groups that a user is a member of.
     *
     * @param id the identifier of the user
     * @return a list of group entities that the user is a member of
     */
    override fun findGroupsByUserId(id: String): List<Group> {
        val groupIds = db.from(Memberships).select(Memberships.groupId).where { Memberships.userId eq id }
        return db.groups.filter { it.id inList groupIds }.toList().map { group ->
            val members = getMembers(group.id)
            group.copy(members = members)
        }
    }
}
