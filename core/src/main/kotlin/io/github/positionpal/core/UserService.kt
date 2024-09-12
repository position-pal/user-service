package io.github.positionpal.core

import UserOuterClass.CreateUserRequest
import UserOuterClass.CreateUserResponse
import UserOuterClass.DeleteUserRequest
import UserOuterClass.DeleteUserResponse
import UserOuterClass.GetUserRequest
import UserOuterClass.GetUserResponse
import UserOuterClass.Status
import UserOuterClass.StatusCode
import UserOuterClass.UpdateUserRequest
import UserOuterClass.UpdateUserResponse
import UserOuterClass.User
import UserServiceGrpc.UserServiceImplBase
import io.github.positionpal.util.StatusUtility.createStatus
/**
 * Service class for managing users.
 */
class UserService : UserServiceImplBase() {

    private val users = mutableMapOf<String, User>()

    /**
     * Creates a new user.
     *
     * @param request the request containing the user information.
     * @return the response containing the created user.
     */
    fun createUser(request: CreateUserRequest): CreateUserResponse {
        val userID = java.util.UUID.randomUUID().toString()

        val user = User.newBuilder()
            .setId(userID)
            .setName(request.user.name)
            .setSurname(request.user.surname)
            .setEmail(request.user.email)
            .setPassword(request.user.password)
            .setRole(request.user.role)
            .build()

        users[user.id] = user
        return CreateUserResponse.newBuilder()
            .setUser(user)
            .setStatus(createStatus(StatusCode.OK, "User created successfully"))
            .build()
    }

    /**
     * Retrieves a user by ID.
     *
     * @param request the request containing the user ID.
     * @return the response containing the user.
     * @throws IllegalArgumentException if the user is not found.
     */
    fun getUser(request: GetUserRequest): GetUserResponse {
        val user = users[request.userId]
        val status = if (user != null) {
            createStatus(StatusCode.OK, "User retrieved successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, "User not found")
        }
        return GetUserResponse.newBuilder()
            .setUser(user ?: User.getDefaultInstance())
            .setStatus(status)
            .build()
    }

    /**
     * Updates an existing user.
     *
     * @param request the request containing the userId and user info.
     * @return the response containing the updated user.
     * @throws IllegalArgumentException if the user is not found.
     */
    fun updateUser(request: UpdateUserRequest): UpdateUserResponse {
        val existingUser = users[request.userId]
        val status: Status
        val updatedUser: User

        if (existingUser != null) {
            updatedUser = existingUser.toBuilder()
                .setName(request.user.name)
                .setSurname(request.user.surname)
                .setEmail(request.user.email)
                .setPassword(request.user.password)
                .setRole(request.user.role)
                .build()

            users[updatedUser.id] = updatedUser
            status = createStatus(StatusCode.OK, "User updated successfully")
        } else {
            updatedUser = User.getDefaultInstance()
            status = createStatus(StatusCode.NOT_FOUND, "User not found")
        }

        return UpdateUserResponse.newBuilder().setUser(updatedUser).setStatus(status).build()
    }

    /**
     * Deletes a user by ID.
     *
     * @param request the request containing the user ID.
     * @return the response containing the ID of the deleted user.
     * @throws IllegalArgumentException if the user is not found.
     */
    fun deleteUser(request: DeleteUserRequest): DeleteUserResponse {
        val user = users.remove(request.userId)
        val status = if (user != null) {
            createStatus(StatusCode.OK, "User deleted successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, "User not found")
        }
        return DeleteUserResponse.newBuilder().setUserId(request.userId).setStatus(status).build()
    }
}
