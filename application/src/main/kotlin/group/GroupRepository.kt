package group

import Group
import User

/**
 * Repository interface for managing Group entities.
 */
interface GroupRepository {

    /**
     * Saves a given group.
     * @param group the group to save
     * @return the saved group with a generated ID
     */
    fun save(group: Group): Group

    /**
     * Retrieves a group by its ID.
     * @param groupId the ID of the group to retrieve
     * @return the group with the given ID, or null if no group found
     */
    fun findById(groupId: String): Group?

    /**
     * Updates a given group.
     * @param group the group to update
     * @return the updated group, or null if the group does not exist
     */
    fun update(group: Group): Group?

    /**
     * Deletes a group by its ID.
     * @param groupId the ID of the group to delete
     * @return true if the group was deleted, false otherwise
     */
    fun deleteById(groupId: String): Boolean

    /**
     * Retrieves all groups.
     * @return a list of all groups
     */
    fun findAll(): List<Group>

    /**
     * Adds a member to a group.
     * @param groupId the ID of the group
     * @param user the user to add
     * @return the updated group, or null if the group does not exist
     */
    fun addMember(groupId: String, user: User): Group?

    /**
     * Removes a member from a group.
     * @param groupId the ID of the group
     * @param user the user to remove
     * @return the updated group, or null if the group does not exist
     */
    fun removeMember(groupId: String, user: User): Group?
}