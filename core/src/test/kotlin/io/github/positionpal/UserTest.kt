package io.github.positionpal

import UserOuterClass.CreateUserRequest
import UserOuterClass.DeleteUserRequest
import UserOuterClass.GetUserRequest
import UserOuterClass.UpdateUserRequest
import UserOuterClass.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UserTest : FunSpec({

    val userService = UserService()

    test("createUser should add a user and return the created user") {
        val user = User.newBuilder()
            .setName("John Doe")
            .setEmail("john.doe@example.com")
            .build()

        val request = CreateUserRequest.newBuilder()
            .setUser(user)
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
        val user = User.newBuilder()
            .setId("john-smith-id")
            .setName("John Smith")
            .setEmail("john.smith@example.com")
            .build()

        val createRequest = CreateUserRequest.newBuilder()
            .setUser(user)
            .build()

        userService.createUser(createRequest)

        val updatedUser = User.newBuilder()
            .setName("John Smith Updated")
            .setEmail("john.smith.updated@example.com")
            .build()

        val updateRequest = UpdateUserRequest.newBuilder()
            .setUserId("john-smith-id")
            .setUser(updatedUser)
            .build()
        val updateResponse = userService.updateUser(updateRequest)

        updateResponse.user.name shouldBe "John Smith Updated"
        updateResponse.user.email shouldBe "john.smith.updated@example.com"
    }

    test("updateUser should throw exception if user does not exist") {
        val updateRequest = UpdateUserRequest.newBuilder()
            .setUserId("non-existent-id")
            .setUser(User.newBuilder().build())
            .build()

        shouldThrow<IllegalArgumentException> {
            userService.updateUser(updateRequest)
        }
    }

    test("deleteUser should remove the user if exists") {
        val user = User.newBuilder()
            .setName("Jane Smith")
            .setEmail("jane.smith@example.com")
            .build()

        val createRequest = CreateUserRequest.newBuilder()
            .setUser(user)
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
