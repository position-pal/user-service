package io.github.positionpal.core

import GroupOuterClass.CreateGroupRequest
import GroupOuterClass.CreateGroupResponse
import GroupOuterClass.DeleteGroupRequest
import GroupOuterClass.DeleteGroupResponse
import GroupOuterClass.GetGroupRequest
import GroupOuterClass.GetGroupResponse
import GroupOuterClass.Group
import GroupOuterClass.UpdateGroupRequest
import GroupOuterClass.UpdateGroupResponse
import GroupServiceGrpc.GroupServiceImplBase
import UserOuterClass.Status
import UserOuterClass.StatusCode
import io.github.positionpal.util.StatusUtility.createStatus

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
        return CreateGroupResponse.newBuilder()
            .setGroup(group)
            .setStatus(createStatus(StatusCode.OK, "Group created successfully"))
            .build()
    }

    /**
     * Retrieves a group by its ID.
     *
     * @param request the request containing the group ID.
     * @return the response containing the group.
     * @throws IllegalArgumentException if the group is not found.
     */
    fun getGroup(request: GetGroupRequest): GetGroupResponse {
        val group = groups.find { it.id == request.groupId }
        val status = if (group != null) {
            createStatus(StatusCode.OK, "Group retrieved successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, "Group not found")
        }
        return GetGroupResponse.newBuilder()
            .setGroup(group ?: Group.getDefaultInstance())
            .setStatus(status)
            .build()
    }

    /**
     * Deletes a group by its ID.
     *
     * @param request the request containing the group ID.
     * @return the response containing the ID of the deleted group.
     * @throws IllegalArgumentException if the group is not found.
     */
    fun deleteGroup(request: DeleteGroupRequest): DeleteGroupResponse {
        val group = groups.find { it.id == request.groupId }
        val status = if (group != null) {
            groups.remove(group)
            createStatus(StatusCode.OK, "Group deleted successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, "Group not found")
        }
        return DeleteGroupResponse.newBuilder()
            .setGroupId(request.groupId)
            .setStatus(status)
            .build()
    }

    /**
     * Updates a group's name by its ID.
     *
     * @param request the request containing the group ID and the new name.
     * @return the response containing the updated group.
     * @throws IllegalArgumentException if the group is not found.
     */
    fun updateGroup(request: UpdateGroupRequest): UpdateGroupResponse {
        val group = groups.find { it.id == request.groupId }
        val status: Status
        val updatedGroup: Group

        if (group != null) {
            updatedGroup = group.toBuilder()
                .setName(request.name)
                .build()
            groups.remove(group)
            groups.add(updatedGroup)
            status = createStatus(StatusCode.OK, "Group updated successfully")
        } else {
            updatedGroup = Group.getDefaultInstance()
            status = createStatus(StatusCode.NOT_FOUND, "Group not found")
        }

        return UpdateGroupResponse.newBuilder()
            .setGroup(updatedGroup)
            .setStatus(status)
            .build()
    }
}
