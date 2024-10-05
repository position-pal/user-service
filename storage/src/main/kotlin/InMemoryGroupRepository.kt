import group.GroupRepository

/**
 * In-memory implementation of the group.GroupRepository interface.
 */
class InMemoryGroupRepository : GroupRepository {

    private val groups = mutableMapOf<String, Group>()

    /**
     * Saves a given group.
     * @param group the group to save
     * @return the saved group with a generated ID
     */
    override fun save(group: Group): Group {
        val groupId = java.util.UUID.randomUUID().toString()
        val newGroup = group.copy(id = groupId)
        groups[groupId] = newGroup
        return newGroup
    }

    /**
     * Retrieves a group by its ID.
     * @param groupId the ID of the group to retrieve
     * @return the group with the given ID, or null if no group found
     */
    override fun findById(groupId: String): Group? = groups[groupId]

    /**
     * Updates a given group.
     * @param group the group to update
     * @return the updated group, or null if the group does not exist
     */
    override fun update(group: Group): Group? =
        groups.takeIf { it.containsKey(group.id) }
            ?.apply { this[group.id] = group }
            ?.let { group }

    /**
     * Deletes a group by its ID.
     * @param groupId the ID of the group to delete
     * @return true if the group was deleted, false otherwise
     */
    override fun deleteById(groupId: String): Boolean = groups.remove(groupId) != null

    /**
     * Retrieves all groups.
     * @return a list of all groups
     */
    override fun findAll(): List<Group> = groups.values.toList()

    /**
     * Adds a member to a group.
     * @param groupId the ID of the group
     * @param userId the ID of the user to add
     * @return the updated group, or null if the group does not exist
     */
    override fun addMember(groupId: String, userId: String): Group? {
        return groups[groupId]?.let { group ->
            val updatedGroup = group.copy(members = group.members + userId)
            groups[groupId] = updatedGroup
            updatedGroup
        }
    }

    /**
     * Removes a member from a group.
     * @param groupId the ID of the group
     * @param userId the ID of the user to remove
     * @return the updated group, or null if the group does not exist
     */
    override fun removeMember(groupId: String, userId: String): Group? {
        return groups[groupId]?.let { group ->
            val updatedGroup = group.copy(members = group.members - userId)
            groups[groupId] = updatedGroup
            updatedGroup
        }
    }
}
