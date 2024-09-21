import Converter.mapFromGrpcGroup
import Converter.mapToGrpcGroup
import GroupOuterClass.AddMemberRequest
import GroupOuterClass.AddMemberResponse
import GroupOuterClass.CreateGroupRequest
import GroupOuterClass.CreateGroupResponse
import GroupOuterClass.DeleteGroupRequest
import GroupOuterClass.DeleteGroupResponse
import GroupOuterClass.GetGroupRequest
import GroupOuterClass.GetGroupResponse
import GroupOuterClass.RemoveMemberRequest
import GroupOuterClass.RemoveMemberResponse
import GroupOuterClass.UpdateGroupRequest
import GroupOuterClass.UpdateGroupResponse
import GroupServiceGrpc.GroupServiceImplBase
import StatusUtility.createStatus
import UserOuterClass.StatusCode
import group.GroupService
import io.grpc.stub.StreamObserver

/**
 * Adapter class for gRPC group.GroupService, implementing the GroupServiceImplBase.
 *
 * @property groupService the service used for managing Group entities
 */
class GrpcGroupServiceAdapter(private val groupService: GroupService) : GroupServiceImplBase() {

    /**
     * Creates a new group.
     * @param request the gRPC request containing group details
     * @param responseObserver the gRPC response observer
     */
    override fun createGroup(request: CreateGroupRequest?, responseObserver: StreamObserver<CreateGroupResponse>?) {
        val group = request?.let { mapFromGrpcGroup(it.group) }

        val createdGroup = group?.let { groupService.createGroup(it) }
        val response = CreateGroupResponse.newBuilder()
            .setGroup(createdGroup?.let { mapToGrpcGroup(it) })
            .setStatus(createStatus(StatusCode.OK, "Group created successfully"))
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    /**
     * Retrieves a group by its ID.
     * @param request the gRPC request containing the group ID
     * @param responseObserver the gRPC response observer
     */
    override fun getGroup(request: GetGroupRequest?, responseObserver: StreamObserver<GetGroupResponse>?) {
        val group = request?.let { groupService.getGroup(it.groupId) }
        val status = group?.let {
            createStatus(StatusCode.OK, "Group retrieved successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, "Group not found")
        val response = GetGroupResponse.newBuilder()
            .setGroup(group?.let { mapToGrpcGroup(it) } ?: GroupOuterClass.Group.getDefaultInstance())
            .setStatus(status)
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    /**
     * Updates an existing group.
     * @param request the gRPC request containing group details
     * @param responseObserver the gRPC response observer
     */
    override fun updateGroup(request: UpdateGroupRequest?, responseObserver: StreamObserver<UpdateGroupResponse>?) {
        val updatedGroup = request?.let {
            mapFromGrpcGroup(it.group)
        }?.let {
            groupService.updateGroup(request.groupId, it)
        }
        val status = updatedGroup?.let {
            createStatus(StatusCode.OK, "Group updated successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, "Group not found")
        val response = UpdateGroupResponse.newBuilder()
            .setGroup(updatedGroup?.let { mapToGrpcGroup(it) } ?: GroupOuterClass.Group.getDefaultInstance())
            .setStatus(status)
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    /**
     * Deletes a group by its ID.
     * @param request the gRPC request containing the group ID
     * @param responseObserver the gRPC response observer
     */
    override fun deleteGroup(request: DeleteGroupRequest?, responseObserver: StreamObserver<DeleteGroupResponse>?) {
        val success = request?.let { groupService.deleteGroup(it.groupId) } ?: false
        val status = if (success) {
            createStatus(StatusCode.OK, "Group deleted successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, "Group not found")
        }
        val response = DeleteGroupResponse.newBuilder()
            .setGroupId(request?.groupId.orEmpty())
            .setStatus(status)
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    /**
     * Adds a member to a group.
     * @param request the gRPC request containing the group ID and user ID
     * @param responseObserver the gRPC response observer
     */
    override fun addMember(request: AddMemberRequest?, responseObserver: StreamObserver<AddMemberResponse>?) {
        val updatedGroup = request?.let { groupService.addMember(it.groupId, it.userId) }
        val status = updatedGroup?.let {
            createStatus(StatusCode.OK, "Member added successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, "Group not found")
        val response = AddMemberResponse.newBuilder()
            .setGroup(updatedGroup?.let { mapToGrpcGroup(it) } ?: GroupOuterClass.Group.getDefaultInstance())
            .setStatus(status)
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    /**
     * Removes a member from a group.
     * @param request the gRPC request containing the group ID and user ID
     * @param responseObserver the gRPC response observer
     */
    override fun removeMember(request: RemoveMemberRequest?, responseObserver: StreamObserver<RemoveMemberResponse>?) {
        val updatedGroup = request?.let { groupService.removeMember(it.groupId, it.userId) }
        val status = updatedGroup?.let {
            createStatus(StatusCode.OK, "Member removed successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, "Group not found")
        val response = RemoveMemberResponse.newBuilder()
            .setGroup(updatedGroup?.let { mapToGrpcGroup(it) } ?: GroupOuterClass.Group.getDefaultInstance())
            .setStatus(status)
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }
}
