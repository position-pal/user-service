package group

import Group
import UserData

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
     * @param userData the user to add
     * @return the updated group, or null if the group does not exist
     */
    fun addMember(groupId: String, userData: UserData): Group?

    /**
     * Removes a member from a group.
     * @param groupId the ID of the group
     * @param userData the user to remove
     * @return the updated group, or null if the group does not exist
     */
    fun removeMember(groupId: String, userData: UserData): Group?

    /**
     * Retrieves all groups of a given user email.
     * @param email the email of the user
     * @return a list of all groups
     */
    fun findAllGroupsOfUser(email: String): List<Group>

    /**
     * Retrieves all groups of a given user email.
     * @param id the id of the user
     * @return a list of all groups
     */
    fun findAllGroupsByUserId(id: String): List<Group>
}
