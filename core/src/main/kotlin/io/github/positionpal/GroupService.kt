package io.github.positionpal

import GroupOuterClass.AddUserToGroupRequest
import GroupOuterClass.CreateGroupRequest
import GroupOuterClass.CreateGroupResponse
import GroupOuterClass.DeleteGroupRequest
import GroupOuterClass.DeleteGroupResponse
import GroupOuterClass.GetGroupRequest
import GroupOuterClass.GetGroupResponse
import GroupOuterClass.Group
import GroupOuterClass.RemoveUserFromGroupRequest
import GroupOuterClass.UpdateGroupRequest
import GroupOuterClass.UpdateGroupResponse
import GroupServiceGrpc.GroupServiceImplBase

/**
 * Service class for managing groups.
 */
class GroupService : GroupServiceImplBase() {

    private val groups = ArrayList<Group>()

    /**
     * Creates a new group.
     *
     * @param request the request containing the group name.
     * @return the response containing the created group.
     */
    fun createGroup(request: CreateGroupRequest): CreateGroupResponse {
        val groupId = java.util.UUID.randomUUID().toString()
        val group = Group.newBuilder()
            .setId(groupId)
            .setName(request.name)
            .build()
        groups.add(group)
        return CreateGroupResponse.newBuilder().setGroup(group).build()
    }

    /**
     * Retrieves a group by its ID.
     *
     * @param request the request containing the group ID.
     * @return the response containing the group.
     * @throws IllegalArgumentException if the group is not found.
     */
    fun getGroup(request: GetGroupRequest): GetGroupResponse {
        val group = groups.find { it.id == request.groupId } ?: throw IllegalArgumentException("Group not found")
        return GetGroupResponse.newBuilder().setGroup(group).build()
    }

    /**
     * Deletes a group by its ID.
     *
     * @param request the request containing the group ID.
     * @return the response containing the ID of the deleted group.
     * @throws IllegalArgumentException if the group is not found.
     */
    fun deleteGroup(request: DeleteGroupRequest): DeleteGroupResponse {
        val group = groups.find { it.id == request.groupId } ?: throw IllegalArgumentException("Group not found")
        groups.remove(group)
        return DeleteGroupResponse.newBuilder().setGroupId(request.groupId).build()
    }

    /**
     * Updates a group's name by its ID.
     *
     * @param request the request containing the group ID and the new name.
     * @return the response containing the updated group.
     * @throws IllegalArgumentException if the group is not found.
     */
    fun updateGroup(request: UpdateGroupRequest): UpdateGroupResponse {
        val group = groups.find { it.id == request.groupId } ?: throw IllegalArgumentException("Group not found")
        val updatedGroup = Group.newBuilder()
            .setId(group.id)
            .setName(request.name)
            .build()
        groups.remove(group)
        groups.add(updatedGroup)
        return UpdateGroupResponse.newBuilder().setGroup(updatedGroup).build()
    }

    /**
     * Adds a user to a group.
     *
     * @param request the request containing the group ID and user information.
     * @throws IllegalArgumentException if the group is not found.
     */
    fun addUserToGroup(request: AddUserToGroupRequest) {
        val group = groups.find { it.id == request.groupId } ?: throw IllegalArgumentException("Group not found")
        // todo
    }

    /**
     * Removes a user from a group.
     *
     * @param request the request containing the group ID and user information.
     * @throws IllegalArgumentException if the group is not found.
     */
    fun removeUserFromGroup(request: RemoveUserFromGroupRequest) {
        val group = groups.find { it.id == request.groupId } ?: throw IllegalArgumentException("Group not found")
        // todo
    }
}
