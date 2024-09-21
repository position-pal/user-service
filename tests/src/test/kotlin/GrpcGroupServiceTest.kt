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
import UserOuterClass.StatusCode
import group.GroupService
import io.grpc.stub.StreamObserver
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify

fun <T : Any> createMockResponseObserver() = mockk<StreamObserver<T>>(relaxed = true)
inline fun <reified T : Any> captureResponse(slot: CapturingSlot<T>, responseObserver: StreamObserver<T>) {
    verify { responseObserver.onNext(capture(slot)) }
    verify { responseObserver.onCompleted() }
}

class GrpcGroupServiceTest : FunSpec({

    val getEmptyUser = Converter.mapFromGrpcUser(UserOuterClass.User.getDefaultInstance())
    val mockGroupService = mockk<GroupService>()
    val grpcAdapter = GrpcGroupServiceAdapter(mockGroupService)

    context("createGroup") {
        test("should create a group and return success status") {
            val grpcGroup = GroupOuterClass.Group.newBuilder().setName("Test Group").build()
            val request = CreateGroupRequest.newBuilder().setGroup(grpcGroup).build()
            val createdGroup = Group(id = "123", name = "Test Group", members = emptyList(), createdBy = getEmptyUser)

            every { mockGroupService.createGroup(any()) } returns createdGroup

            val responseObserver = createMockResponseObserver<CreateGroupResponse>()
            grpcAdapter.createGroup(request, responseObserver)

            val slot = slot<CreateGroupResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.OK
            response.group.name shouldBe "Test Group"
        }
    }

    context("getGroup") {
        test("should return a group if it exists") {
            val retrievedGroup = Group(id = "123", name = "Test Group", members = emptyList(), createdBy = getEmptyUser)
            every { mockGroupService.getGroup("123") } returns retrievedGroup

            val request = GetGroupRequest.newBuilder().setGroupId("123").build()
            val responseObserver = createMockResponseObserver<GetGroupResponse>()
            grpcAdapter.getGroup(request, responseObserver)

            val slot = slot<GetGroupResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.OK
            response.group.name shouldBe "Test Group"
        }

        test("should return NOT_FOUND if group does not exist") {
            every { mockGroupService.getGroup("non-existent-id") } returns null

            val request = GetGroupRequest.newBuilder().setGroupId("non-existent-id").build()
            val responseObserver = createMockResponseObserver<GetGroupResponse>()
            grpcAdapter.getGroup(request, responseObserver)

            val slot = slot<GetGroupResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.NOT_FOUND
            response.group.name shouldBe ""
        }
    }

    context("updateGroup") {
        test("should update a group and return success status") {
            val grpcGroup = GroupOuterClass.Group.newBuilder().setId("123").setName("Updated Group").build()
            val request = UpdateGroupRequest.newBuilder().setGroupId("123").setGroup(grpcGroup).build()
            val updatedGroup = Group(
                id = "123",
                name = "Updated Group",
                members = emptyList(),
                createdBy = getEmptyUser,
            )

            every { mockGroupService.updateGroup("123", any()) } returns updatedGroup

            val responseObserver = createMockResponseObserver<UpdateGroupResponse>()
            grpcAdapter.updateGroup(request, responseObserver)

            val slot = slot<UpdateGroupResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.OK
            response.group.name shouldBe "Updated Group"
        }

        test("should return NOT_FOUND if group does not exist") {
            every { mockGroupService.updateGroup("non-existent-id", any()) } returns null

            val grpcGroup = GroupOuterClass.Group.newBuilder()
                .setId("non-existent-id")
                .setName("NonExistent Group")
                .build()
            val request = UpdateGroupRequest.newBuilder().setGroupId("non-existent-id").setGroup(grpcGroup).build()
            val responseObserver = createMockResponseObserver<UpdateGroupResponse>()
            grpcAdapter.updateGroup(request, responseObserver)

            val slot = slot<UpdateGroupResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.NOT_FOUND
            response.group.name shouldBe ""
        }
    }

    context("deleteGroup") {
        test("should delete a group and return success status") {
            every { mockGroupService.deleteGroup("123") } returns true

            val request = DeleteGroupRequest.newBuilder().setGroupId("123").build()
            val responseObserver = createMockResponseObserver<DeleteGroupResponse>()
            grpcAdapter.deleteGroup(request, responseObserver)

            val slot = slot<DeleteGroupResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.OK
            response.groupId shouldBe "123"
        }

        test("should return NOT_FOUND if group does not exist") {
            every { mockGroupService.deleteGroup("non-existent-id") } returns false

            val request = DeleteGroupRequest.newBuilder().setGroupId("non-existent-id").build()
            val responseObserver = createMockResponseObserver<DeleteGroupResponse>()
            grpcAdapter.deleteGroup(request, responseObserver)

            val slot = slot<DeleteGroupResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.NOT_FOUND
            response.groupId shouldBe "non-existent-id"
        }
    }

    context("addMember") {
        test("should add a member to a group and return success status") {
            val updatedGroup = Group(
                id = "123",
                name = "Test Group",
                members = listOf("user1"),
                createdBy = getEmptyUser,
            )
            every { mockGroupService.addMember("123", "user1") } returns updatedGroup

            val request = AddMemberRequest.newBuilder().setGroupId("123").setUserId("user1").build()
            val responseObserver = createMockResponseObserver<AddMemberResponse>()
            grpcAdapter.addMember(request, responseObserver)

            val slot = slot<AddMemberResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.OK
            response.group.membersList shouldBe listOf("user1")
        }

        test("should return NOT_FOUND if group does not exist") {
            every { mockGroupService.addMember("non-existent-id", "user1") } returns null

            val request = AddMemberRequest.newBuilder().setGroupId("non-existent-id").setUserId("user1").build()
            val responseObserver = createMockResponseObserver<AddMemberResponse>()
            grpcAdapter.addMember(request, responseObserver)

            val slot = slot<AddMemberResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.NOT_FOUND
            response.group.membersList shouldBe emptyList()
        }
    }

    context("removeMember") {
        test("should remove a member from a group and return success status") {
            val updatedGroup = Group(id = "123", name = "Test Group", members = emptyList(), createdBy = getEmptyUser)
            every { mockGroupService.removeMember("123", "user1") } returns updatedGroup

            val request = RemoveMemberRequest.newBuilder().setGroupId("123").setUserId("user1").build()
            val responseObserver = createMockResponseObserver<RemoveMemberResponse>()
            grpcAdapter.removeMember(request, responseObserver)

            val slot = slot<RemoveMemberResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.OK
            response.group.membersList shouldBe emptyList()
        }

        test("should return NOT_FOUND if group does not exist") {
            every { mockGroupService.removeMember("non-existent-id", "user1") } returns null

            val request = RemoveMemberRequest.newBuilder().setGroupId("non-existent-id").setUserId("user1").build()
            val responseObserver = createMockResponseObserver<RemoveMemberResponse>()
            grpcAdapter.removeMember(request, responseObserver)

            val slot = slot<RemoveMemberResponse>()
            captureResponse(slot, responseObserver)

            val response = slot.captured
            response.status.code shouldBe StatusCode.NOT_FOUND
            response.group.membersList shouldBe emptyList()
        }
    }
})
