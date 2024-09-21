package group

import Group

/**
 * Service interface for managing Group entities.
 */
interface GroupService {

    /**
     * Creates a new group.
     * @param group the group to create
     * @return the created group with a generated ID
     */
    fun createGroup(group: Group): Group

    /**
     * Retrieves a group by its ID.
     * @param groupId the ID of the group to retrieve
     * @return the group with the given ID, or null if no group found
     */
    fun getGroup(groupId: String): Group?

    /**
     * Updates an existing group.
     * @param groupId the ID of the group to update
     * @param group the group with updated details
     * @return the updated group, or null if the group does not exist
     */
    fun updateGroup(groupId: String, group: Group): Group?

    /**
     * Deletes a group by its ID.
     * @param groupId the ID of the group to delete
     * @return true if the group was deleted, false otherwise
     */
    fun deleteGroup(groupId: String): Boolean

    /**
     * Adds a member to a group.
     * @param groupId the ID of the group
     * @param userId the ID of the user to add
     * @return the updated group, or null if the group does not exist
     */
    fun addMember(groupId: String, userId: String): Group?

    /**
     * Removes a member from a group.
     * @param groupId the ID of the group
     * @param userId the ID of the user to remove
     * @return the updated group, or null if the group does not exist
     */
    fun removeMember(groupId: String, userId: String): Group?
}
