package io.github.positionpal

import UserOuterClass.CreateUserRequest
import UserOuterClass.DeleteUserRequest
import UserOuterClass.GetUserRequest
import UserOuterClass.StatusCode
import UserOuterClass.UpdateUserRequest
import UserOuterClass.User
import io.github.positionpal.core.UserService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UserTest : FunSpec({

    val userService = UserService()
    context("createUser") {
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
            response.status.code shouldBe StatusCode.OK
        }
    }
    context("getUser") {

        test("getUser should return a user given an id") {
            val createUserRequest = CreateUserRequest.newBuilder()
                .setUser(
                    User.newBuilder()
                        .setName("John Doe")
                        .setEmail("john.doe@example.com")
                        .build(),
                )
                .build()

            val createUserResponse = userService.createUser(createUserRequest)
            val userId = createUserResponse.user.id

            val getRequest = GetUserRequest.newBuilder()
                .setUserId(userId)
                .build()

            val response = userService.getUser(getRequest)
            response.user.name shouldBe "John Doe"
            response.user.email shouldBe "john.doe@example.com"
            response.status.code shouldBe StatusCode.OK
        }

        test("getUser should return not found status if user does not exist") {
            val getRequest = GetUserRequest.newBuilder()
                .setUserId("non-existent-id")
                .build()

            val response = userService.getUser(getRequest)
            response.status.code shouldBe StatusCode.NOT_FOUND
        }
    }

    context("updateUser") {
        test("updateUser should update the user details if user exists") {
            val createUserRequest = CreateUserRequest.newBuilder()
                .setUser(
                    User.newBuilder()
                        .setName("John Smith")
                        .setEmail("john.smith@example.com")
                        .build(),
                )
                .build()

            val createUserResponse = userService.createUser(createUserRequest)
            val userId = createUserResponse.user.id

            val updatedUser = User.newBuilder()
                .setName("John Smith Updated")
                .setEmail("john.smith.updated@example.com")
                .build()

            val updateRequest = UpdateUserRequest.newBuilder()
                .setUserId(userId)
                .setUser(updatedUser)
                .build()
            val updateResponse = userService.updateUser(updateRequest)

            updateResponse.user.name shouldBe "John Smith Updated"
            updateResponse.user.email shouldBe "john.smith.updated@example.com"
            updateResponse.status.code shouldBe StatusCode.OK
        }

        test("updateUser should return not found status if user does not exist") {
            val updateRequest = UpdateUserRequest.newBuilder()
                .setUserId("non-existent-id")
                .setUser(User.newBuilder().build())
                .build()

            val response = userService.updateUser(updateRequest)
            response.status.code shouldBe StatusCode.NOT_FOUND
        }
    }

    context("deleteUser") {
        test("deleteUser should remove the user if exists") {
            val createUserRequest = CreateUserRequest.newBuilder()
                .setUser(
                    User.newBuilder()
                        .setName("Jane Smith")
                        .setEmail("jane.smith@example.com")
                        .build(),
                )
                .build()
            val createResponse = userService.createUser(createUserRequest)
            val userId = createResponse.user.id

            val deleteRequest = DeleteUserRequest.newBuilder()
                .setUserId(userId)
                .build()
            val deleteResponse = userService.deleteUser(deleteRequest)

            deleteResponse.userId shouldBe userId
            deleteResponse.status.code shouldBe StatusCode.OK

            val response = userService.getUser(GetUserRequest.newBuilder().setUserId(userId).build())
            response.status.code shouldBe StatusCode.NOT_FOUND
        }

        test("deleteUser should return not found status if user does not exist") {
            val deleteRequest = DeleteUserRequest.newBuilder()
                .setUserId("non-existent-id")
                .build()

            val response = userService.deleteUser(deleteRequest)
            response.status.code shouldBe StatusCode.NOT_FOUND
        }
    }
})
