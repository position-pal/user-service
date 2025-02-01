package group

import Group
import MessageAdapter
import UserData
import io.github.positionpal.events.AddedMemberToGroup
import io.github.positionpal.events.EventType
import io.github.positionpal.events.GroupCreated
import io.github.positionpal.events.GroupDeleted
import io.github.positionpal.events.RemovedMemberToGroup

/**
 * Implementation of the group.GroupService interface.
 *
 * @property groupRepository the repository used for managing Group entities
 */
class GroupServiceImpl(
    private val groupRepository: GroupRepository,
    private val messageAdapter: MessageAdapter,
) : GroupService {

    /**
     * Creates a new group.
     * @param group the group to create
     * @return the created group
     */
    override fun createGroup(group: Group): Group {
        val event = GroupCreated.create(
            { group.id },
            io.github.positionpal.entities.User.create(
                { group.createdBy.id },
                group.createdBy.name,
                group.createdBy.surname,
                group.createdBy.email,
            ),
        )
        messageAdapter.postEvent(EventType.GROUP_CREATED, event)
        return groupRepository.save(group)
    }

    /**
     * Retrieves a group by its ID.
     * @param groupId the ID of the group to retrieve
     * @return the group with the given ID, or null if nox group found
     */
    override fun getGroup(groupId: String): Group? = groupRepository.findById(groupId)

    /**
     * Updates an existing group.
     * @param groupId the ID of the group to update
     * @param group the group data to update
     * @return the updated group, or null if the group does not exist
     */
    override fun updateGroup(groupId: String, group: Group): Group? =
        groupRepository.findById(groupId)?.let {
            val updatedGroup = group.copy(id = groupId)
            groupRepository.update(updatedGroup)
        }

    /**
     * Deletes a group by its ID.
     * @param groupId the ID of the group to delete
     * @return true if the group was deleted, false otherwise
     */
    override fun deleteGroup(groupId: String): Boolean {
        val event = GroupDeleted.create { groupId }
        messageAdapter.postEvent(EventType.GROUP_DELETED, event)
        return groupRepository.deleteById(groupId)
    }

    /**
     * Adds a member to a group.
     * @param groupId the ID of the group
     * @param userData the user data to add
     * @return the updated group, or null if the group does not exist
     */
    override fun addMember(groupId: String, userData: UserData): Group? {
        val event = AddedMemberToGroup.create(
            { groupId },
            io.github.positionpal.entities.User.create(
                { userData.id },
                userData.name,
                userData.surname,
                userData.email,
            ),
        )
        messageAdapter.postEvent(EventType.MEMBER_ADDED, event)
        return groupRepository.addMember(groupId, userData)
    }

    /**
     * Removes a member from a group.
     * @param groupId the ID of the group
     * @param userData the ID of the user to remove
     * @return the updated group, or null if the group does not exist
     */
    override fun removeMember(groupId: String, userData: UserData): Group? {
        val event = RemovedMemberToGroup.create(
            { groupId },
            io.github.positionpal.entities.User.create(
                { userData.id },
                userData.name,
                userData.surname,
                userData.email,
            ),
        )
        messageAdapter.postEvent(EventType.MEMBER_REMOVED, event)
        return groupRepository.removeMember(groupId, userData)
    }
}
