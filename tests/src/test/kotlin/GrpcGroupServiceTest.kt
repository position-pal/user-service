import GroupOuterClass.AddMemberRequest
import GroupOuterClass.CreateGroupRequest
import GroupOuterClass.DeleteGroupRequest
import GroupOuterClass.GetGroupRequest
import GroupOuterClass.RemoveMemberRequest
import GroupOuterClass.UpdateGroupRequest
import UserOuterClass.StatusCode
import group.GroupService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking

class GrpcGroupServiceTest : FunSpec({

    val mockGroupService = mockk<GroupService>()
    val grpcAdapter = GrpcGroupServiceAdapter(mockGroupService)

    context("createGroup") {
        test("should create a group and return success status") {
            val grpcGroup = GroupOuterClass.Group.newBuilder().setName("Test Group").build()
            val request = CreateGroupRequest.newBuilder().setGroup(grpcGroup).build()
            val createdGroup = Group(
                id = "123",
                name = "Test Group",
                members = emptyList(),
                createdBy = User.getEmptyUser(),
            )

            coEvery { mockGroupService.createGroup(any()) } returns createdGroup

            val response = runBlocking { grpcAdapter.createGroup(request) }

            response.status.code shouldBe StatusCode.OK
            response.group.name shouldBe "Test Group"
        }
    }

    context("getGroup") {
        test("should return a group if it exists") {
            val retrievedGroup = Group(
                id = "123",
                name = "Test Group",
                members = emptyList(),
                createdBy = User.getEmptyUser(),
            )
            coEvery { mockGroupService.getGroup("123") } returns retrievedGroup

            val request = GetGroupRequest.newBuilder().setGroupId("123").build()
            val response = runBlocking { grpcAdapter.getGroup(request) }

            response.status.code shouldBe StatusCode.OK
            response.group.name shouldBe "Test Group"
        }

        test("should return NOT_FOUND if group does not exist") {
            coEvery { mockGroupService.getGroup("non-existent-id") } returns null

            val request = GetGroupRequest.newBuilder().setGroupId("non-existent-id").build()
            val response = runBlocking { grpcAdapter.getGroup(request) }

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
                createdBy = User.getEmptyUser(),
            )

            coEvery { mockGroupService.updateGroup("123", any()) } returns updatedGroup

            val response = runBlocking { grpcAdapter.updateGroup(request) }

            response.status.code shouldBe StatusCode.OK
            response.group.name shouldBe "Updated Group"
        }

        test("should return NOT_FOUND if group does not exist") {
            coEvery { mockGroupService.updateGroup("non-existent-id", any()) } returns null

            val grpcGroup = GroupOuterClass.Group.newBuilder()
                .setId("non-existent-id")
                .setName("NonExistent Group")
                .build()
            val request = UpdateGroupRequest.newBuilder().setGroupId("non-existent-id").setGroup(grpcGroup).build()
            val response = runBlocking { grpcAdapter.updateGroup(request) }

            response.status.code shouldBe StatusCode.NOT_FOUND
            response.group.name shouldBe ""
        }
    }

    context("deleteGroup") {
        test("should delete a group and return success status") {
            coEvery { mockGroupService.deleteGroup("123") } returns true

            val request = DeleteGroupRequest.newBuilder().setGroupId("123").build()
            val response = runBlocking { grpcAdapter.deleteGroup(request) }

            response.status.code shouldBe StatusCode.OK
            response.groupId shouldBe "123"

            coEvery { mockGroupService.getGroup("123") } returns null

            val request2 = GetGroupRequest.newBuilder().setGroupId("123").build()
            val response2 = runBlocking { grpcAdapter.getGroup(request2) }
            response2.status.code shouldBe StatusCode.NOT_FOUND
        }

        test("should return NOT_FOUND if group does not exist") {
            coEvery { mockGroupService.deleteGroup("non-existent-id") } returns false

            val request = DeleteGroupRequest.newBuilder().setGroupId("non-existent-id").build()
            val response = runBlocking { grpcAdapter.deleteGroup(request) }

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
                createdBy = User.getEmptyUser(),
            )
            coEvery { mockGroupService.addMember("123", "user1") } returns updatedGroup

            val request = AddMemberRequest.newBuilder().setGroupId("123").setUserId("user1").build()
            val response = runBlocking { grpcAdapter.addMember(request) }

            response.status.code shouldBe StatusCode.OK
            response.group.membersList shouldBe listOf("user1")
        }

        test("should return NOT_FOUND if group does not exist") {
            coEvery { mockGroupService.addMember("non-existent-id", "user1") } returns null

            val request = AddMemberRequest.newBuilder().setGroupId("non-existent-id").setUserId("user1").build()
            val response = runBlocking { grpcAdapter.addMember(request) }

            response.status.code shouldBe StatusCode.NOT_FOUND
            response.group.membersList shouldBe emptyList()
        }
    }

    context("removeMember") {
        test("should remove a member from a group and return success status") {
            val updatedGroup = Group(
                id = "123",
                name = "Test Group",
                members = emptyList(),
                createdBy = User.getEmptyUser(),
            )
            coEvery { mockGroupService.removeMember("123", "user1") } returns updatedGroup

            val request = RemoveMemberRequest.newBuilder().setGroupId("123").setUserId("user1").build()
            val response = runBlocking { grpcAdapter.removeMember(request) }

            response.status.code shouldBe StatusCode.OK
            response.group.membersList shouldBe emptyList()
        }

        test("should return NOT_FOUND if group does not exist") {
            coEvery { mockGroupService.removeMember("non-existent-id", "user1") } returns null

            val request = RemoveMemberRequest.newBuilder().setGroupId("non-existent-id").setUserId("user1").build()
            val response = runBlocking { grpcAdapter.removeMember(request) }

            response.status.code shouldBe StatusCode.NOT_FOUND
            response.group.membersList shouldBe emptyList()
        }
    }
})
