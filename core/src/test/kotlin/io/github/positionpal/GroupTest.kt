package io.github.positionpal

import GroupOuterClass.CreateGroupRequest
import GroupOuterClass.DeleteGroupRequest
import GroupOuterClass.GetGroupRequest
import GroupOuterClass.UpdateGroupRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GroupTest : FunSpec({

    val groupService = GroupService()

    context("createGroup") {
        test("creates a group successfully") {
            val request = CreateGroupRequest.newBuilder().setName("Test Group").build()
            val response = groupService.createGroup(request)
            response.group.name shouldBe "Test Group"
        }
    }

    context("getGroup") {
        test("retrieves an existing group") {
            val createRequest = CreateGroupRequest.newBuilder().setName("Test Group").build()
            val createResponse = groupService.createGroup(createRequest)
            val getRequest = GetGroupRequest.newBuilder().setGroupId(createResponse.group.id).build()
            val getResponse = groupService.getGroup(getRequest)
            getResponse.group.name shouldBe "Test Group"
        }

        test("throws exception when group not found") {
            val getRequest = GetGroupRequest.newBuilder().setGroupId("nonexistent").build()
            shouldThrow<IllegalArgumentException> {
                groupService.getGroup(getRequest)
            }
        }
    }

    context("deleteGroup") {
        test("deletes an existing group") {
            val createRequest = CreateGroupRequest.newBuilder().setName("Test Group").build()
            val createResponse = groupService.createGroup(createRequest)
            val deleteRequest = DeleteGroupRequest.newBuilder().setGroupId(createResponse.group.id).build()
            val deleteResponse = groupService.deleteGroup(deleteRequest)
            deleteResponse.groupId shouldBe createResponse.group.id
        }

        test("throws exception when group not found") {
            val deleteRequest = DeleteGroupRequest.newBuilder().setGroupId("nonexistent").build()
            shouldThrow<IllegalArgumentException> {
                groupService.deleteGroup(deleteRequest)
            }
        }
    }

    context("updateGroup") {
        test("updates an existing group") {
            val createRequest = CreateGroupRequest.newBuilder().setName("Test Group").build()
            val createResponse = groupService.createGroup(createRequest)
            val updateRequest = UpdateGroupRequest.newBuilder()
                .setGroupId(createResponse.group.id)
                .setName("Updated Group")
                .build()
            val updateResponse = groupService.updateGroup(updateRequest)
            updateResponse.group.name shouldBe "Updated Group"
        }

        test("throws exception when group not found") {
            val updateRequest = UpdateGroupRequest.newBuilder()
                .setGroupId("nonexistent")
                .setName("Updated Group")
                .build()
            shouldThrow<IllegalArgumentException> {
                groupService.updateGroup(updateRequest)
            }
        }
    }
})
