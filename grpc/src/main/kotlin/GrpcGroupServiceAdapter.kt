import Converter.mapFromGrpcGroup
import Converter.mapFromGrpcUserData
import Converter.mapToGrpcGroup
import GroupOuterClass.AddMemberRequest
import GroupOuterClass.AddMemberResponse
import GroupOuterClass.CreateGroupRequest
import GroupOuterClass.CreateGroupResponse
import GroupOuterClass.DeleteGroupRequest
import GroupOuterClass.DeleteGroupResponse
import GroupOuterClass.GetGroupRequest
import GroupOuterClass.GetGroupResponse
import GroupOuterClass.Group
import GroupOuterClass.RemoveMemberRequest
import GroupOuterClass.RemoveMemberResponse
import GroupOuterClass.UpdateGroupRequest
import GroupOuterClass.UpdateGroupResponse
import GroupServiceGrpcKt.GroupServiceCoroutineImplBase
import StatusUtility.createStatus
import UserOuterClass.StatusCode
import group.GroupService

/**
 * Constant message used when a group is not found.
 */
const val GROUP_NOT_FOUND_MESSAGE = "Group not found"

/**
 * Adapter class for gRPC Group Service.
 * This class is responsible for adapting the gRPC service methods to the internal service methods.
 */
class GrpcGroupServiceAdapter(
    private val groupService: GroupService,
) : GroupServiceCoroutineImplBase() {

    override suspend fun createGroup(request: CreateGroupRequest): CreateGroupResponse {
        val group = mapFromGrpcGroup(request.group)
        val createdGroup = groupService.createGroup(group)
        return CreateGroupResponse.newBuilder()
            .setGroup(mapToGrpcGroup(createdGroup))
            .setStatus(createStatus(StatusCode.OK, "Group created successfully"))
            .build()
    }

    override suspend fun getGroup(request: GetGroupRequest): GetGroupResponse {
        val group = groupService.getGroup(request.groupId)
        val status = group?.let {
            createStatus(StatusCode.OK, "Group retrieved successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, GROUP_NOT_FOUND_MESSAGE)
        return GetGroupResponse.newBuilder()
            .setGroup(group?.let { mapToGrpcGroup(it) } ?: Group.getDefaultInstance())
            .setStatus(status)
            .build()
    }

    override suspend fun updateGroup(request: UpdateGroupRequest): UpdateGroupResponse {
        val updatedGroup = groupService.updateGroup(request.groupId, mapFromGrpcGroup(request.group))
        val status = updatedGroup?.let {
            createStatus(StatusCode.OK, "Group updated successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, GROUP_NOT_FOUND_MESSAGE)
        return UpdateGroupResponse.newBuilder()
            .setGroup(updatedGroup?.let { mapToGrpcGroup(it) } ?: Group.getDefaultInstance())
            .setStatus(status)
            .build()
    }

    override suspend fun deleteGroup(request: DeleteGroupRequest): DeleteGroupResponse {
        val success = groupService.deleteGroup(request.groupId)
        val status = if (success) {
            createStatus(StatusCode.OK, "Group deleted successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, GROUP_NOT_FOUND_MESSAGE)
        }
        return DeleteGroupResponse.newBuilder()
            .setGroupId(request.groupId)
            .setStatus(status)
            .build()
    }

    override suspend fun addMember(request: AddMemberRequest): AddMemberResponse {
        val updatedGroup = groupService.addMember(request.groupId, mapFromGrpcUserData(request.user))
        val status = updatedGroup?.let {
            createStatus(StatusCode.OK, "Member added successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, GROUP_NOT_FOUND_MESSAGE)
        return AddMemberResponse.newBuilder()
            .setGroup(updatedGroup?.let { mapToGrpcGroup(it) } ?: Group.getDefaultInstance())
            .setStatus(status)
            .build()
    }

    override suspend fun removeMember(request: RemoveMemberRequest): RemoveMemberResponse {
        val updatedGroup = groupService.removeMember(request.groupId, mapFromGrpcUserData(request.user))
        val status = updatedGroup?.let {
            createStatus(StatusCode.OK, "Member removed successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, GROUP_NOT_FOUND_MESSAGE)
        return RemoveMemberResponse.newBuilder()
            .setGroup(updatedGroup?.let { mapToGrpcGroup(it) } ?: Group.getDefaultInstance())
            .setStatus(status)
            .build()
    }
}
