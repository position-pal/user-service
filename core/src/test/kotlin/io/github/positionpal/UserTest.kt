package io.github.positionpal

import UserOuterClass.CreateUserRequest
import UserOuterClass.DeleteUserRequest
import UserOuterClass.GetUserRequest
import UserOuterClass.UpdateUserRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UserTest : FunSpec({

    val userService = UserService()

    test("createUser should add a user and return the created user") {
        val request = CreateUserRequest.newBuilder()
            .setName("John Doe")
            .setEmail("john.doe@example.com")
            .build()

        val response = userService.createUser(request)

        response.user.name shouldBe "John Doe"
        response.user.email shouldBe "john.doe@example.com"
    }

    test("getUser should throw exception if user does not exist") {
        val getRequest = GetUserRequest.newBuilder()
            .setUserId("non-existent-id")
            .build()

        shouldThrow<IllegalArgumentException> {
            userService.getUser(getRequest)
        }
    }

    test("updateUser should update the user details if user exists") {
        val createRequest = CreateUserRequest.newBuilder()
            .setName("John Smith")
            .setEmail("john.smith@example.com")
            .build()
        val createResponse = userService.createUser(createRequest)

        val updateRequest = UpdateUserRequest.newBuilder()
            .setUserId(createResponse.user.id)
            .setName("John Smith Updated")
            .setEmail("john.smith.updated@example.com")
            .build()
        val updateResponse = userService.updateUser(updateRequest)

        updateResponse.user.name shouldBe "John Smith Updated"
        updateResponse.user.email shouldBe "john.smith.updated@example.com"
    }

    test("updateUser should throw exception if user does not exist") {
        val updateRequest = UpdateUserRequest.newBuilder()
            .setUserId("non-existent-id")
            .setName("Non Existent")
            .setEmail("non.existent@example.com")
            .build()

        shouldThrow<IllegalArgumentException> {
            userService.updateUser(updateRequest)
        }
    }

    test("deleteUser should remove the user if exists") {
        val createRequest = CreateUserRequest.newBuilder()
            .setName("Jane Smith")
            .setEmail("jane.smith@example.com")
            .build()
        val createResponse = userService.createUser(createRequest)

        val deleteRequest = DeleteUserRequest.newBuilder()
            .setUserId(createResponse.user.id)
            .build()
        val deleteResponse = userService.deleteUser(deleteRequest)

        deleteResponse.userId shouldBe createResponse.user.id
        shouldThrow<IllegalArgumentException> {
            userService.getUser(GetUserRequest.newBuilder().setUserId(createResponse.user.id).build())
        }
    }

    test("deleteUser should throw exception if user does not exist") {
        val deleteRequest = DeleteUserRequest.newBuilder()
            .setUserId("non-existent-id")
            .build()

        shouldThrow<IllegalArgumentException> {
            userService.deleteUser(deleteRequest)
        }
    }
})
