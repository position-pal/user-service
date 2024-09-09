package io.github.positionpal

import UserOuterClass.CreateUserRequest
import UserOuterClass.CreateUserResponse
import UserOuterClass.DeleteUserRequest
import UserOuterClass.DeleteUserResponse
import UserOuterClass.GetUserRequest
import UserOuterClass.GetUserResponse
import UserOuterClass.UpdateUserRequest
import UserOuterClass.UpdateUserResponse
import UserOuterClass.User
import UserServiceGrpc.UserServiceImplBase

/**
 * Service class for managing users.
 */
class UserService : UserServiceImplBase() {

    private val users = mutableMapOf<String, User>()

    /**
     * Creates a new user.
     *
     * @param request the request containing the user's name and email.
     * @return the response containing the created user.
     */
    fun createUser(request: CreateUserRequest): CreateUserResponse {
        val user = User.newBuilder()
            .setId(users.size.toString())
            .setName(request.name)
            .setEmail(request.email)
            .build()

        users[user.id] = user
        return CreateUserResponse.newBuilder().setUser(user).build()
    }

    /**
     * Retrieves a user by ID.
     *
     * @param request the request containing the user ID.
     * @return the response containing the user.
     * @throws IllegalArgumentException if the user is not found.
     */
    fun getUser(request: GetUserRequest): GetUserResponse {
        val user = users[request.userId] ?: throw IllegalArgumentException("Utente non trovato")
        return GetUserResponse.newBuilder().setUser(user).build()
    }

    /**
     * Updates an existing user.
     *
     * @param request the request containing the user ID, name, and email.
     * @return the response containing the updated user.
     * @throws IllegalArgumentException if the user is not found.
     */
    fun updateUser(request: UpdateUserRequest): UpdateUserResponse {
        val existingUser = users[request.userId] ?: throw IllegalArgumentException("Utente non trovato")

        val updatedUser = existingUser.toBuilder()
            .setName(request.name)
            .setEmail(request.email)
            .build()

        users[updatedUser.id] = updatedUser
        return UpdateUserResponse.newBuilder().setUser(updatedUser).build()
    }

    /**
     * Deletes a user by ID.
     *
     * @param request the request containing the user ID.
     * @return the response containing the ID of the deleted user.
     * @throws IllegalArgumentException if the user is not found.
     */
    fun deleteUser(request: DeleteUserRequest): DeleteUserResponse {
        users.remove(request.userId) ?: throw IllegalArgumentException("Utente non trovato")
        return DeleteUserResponse.newBuilder().setUserId(request.userId).build()
    }
}
