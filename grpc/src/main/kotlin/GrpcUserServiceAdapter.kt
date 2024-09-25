import Converter.mapFromGrpcUser
import Converter.mapToGrpcUser
import StatusUtility.createStatus
import UserOuterClass.CreateUserRequest
import UserOuterClass.CreateUserResponse
import UserOuterClass.DeleteUserRequest
import UserOuterClass.DeleteUserResponse
import UserOuterClass.GetUserRequest
import UserOuterClass.GetUserResponse
import UserOuterClass.StatusCode
import UserOuterClass.UpdateUserRequest
import UserOuterClass.UpdateUserResponse
import UserOuterClass.User
import UserServiceGrpcKt.UserServiceCoroutineImplBase
import user.UserService

/**
 * Adapter class for gRPC Group Service.
 * This class is responsible for adapting the gRPC service methods to the internal service methods.
 */
class GrpcUserServiceAdapter(private val userService: UserService) : UserServiceCoroutineImplBase() {
    override suspend fun createUser(request: CreateUserRequest): CreateUserResponse {
        val user = mapFromGrpcUser(request.user)
        val createdUser = userService.createUser(user)
        return CreateUserResponse.newBuilder()
            .setUser(mapToGrpcUser(createdUser))
            .setStatus(createStatus(StatusCode.OK, "User created successfully"))
            .build()
    }

    override suspend fun getUser(request: GetUserRequest): GetUserResponse {
        val user = userService.getUser(request.userId)
        val status = user?.let {
            createStatus(StatusCode.OK, "User retrieved successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, "User not found")
        return GetUserResponse.newBuilder()
            .setUser(user?.let { mapToGrpcUser(it) } ?: User.getDefaultInstance())
            .setStatus(status)
            .build()
    }

    override suspend fun updateUser(request: UpdateUserRequest): UpdateUserResponse {
        val updatedUser = userService.updateUser(request.userId, mapFromGrpcUser(request.user))
        val status = updatedUser?.let {
            createStatus(StatusCode.OK, "User updated successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, "User not found")
        return UpdateUserResponse.newBuilder()
            .setUser(updatedUser?.let { mapToGrpcUser(it) } ?: User.getDefaultInstance())
            .setStatus(status)
            .build()
    }

    override suspend fun deleteUser(request: DeleteUserRequest): DeleteUserResponse {
        val success = userService.deleteUser(request.userId)
        val status = if (success) {
            createStatus(StatusCode.OK, "User deleted successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, "User not found")
        }
        return DeleteUserResponse.newBuilder()
            .setUserId(request.userId)
            .setStatus(status)
            .build()
    }
}
