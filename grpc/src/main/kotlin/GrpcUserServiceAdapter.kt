import Converter.mapFromGrpcUser
import Converter.mapToGrpcUser
import Converter.mapToGrpcUserData
import StatusUtility.createStatus
import UserOuterClass.CreateUserRequest
import UserOuterClass.CreateUserResponse
import UserOuterClass.DeleteUserRequest
import UserOuterClass.DeleteUserResponse
import UserOuterClass.GetUserByEmailRequest
import UserOuterClass.GetUserByEmailResponse
import UserOuterClass.GetUserRequest
import UserOuterClass.GetUserResponse
import UserOuterClass.StatusCode
import UserOuterClass.UpdateUserRequest
import UserOuterClass.UpdateUserResponse
import UserOuterClass.UserData
import UserServiceGrpcKt.UserServiceCoroutineImplBase
import user.UserService

/**
 * Constant message used when a user is not found.
 */
const val USER_NOT_FOUND_MESSAGE = "User not found"

/**
 * Adapter class for gRPC Group Service.
 * This class is responsible for adapting the gRPC service methods to the internal service methods.
 */
class GrpcUserServiceAdapter(private val userService: UserService) : UserServiceCoroutineImplBase() {
    override suspend fun createUser(request: CreateUserRequest): CreateUserResponse {
        val user = mapFromGrpcUser(request.user)
        val createdUser = userService.createUser(user)
        return CreateUserResponse.newBuilder()
            .setUser(mapToGrpcUser(createdUser).userData)
            .setStatus(createStatus(StatusCode.OK, "User created successfully"))
            .build()
    }

    override suspend fun getUser(request: GetUserRequest): GetUserResponse {
        val user = userService.getUser(request.userId)
        val status = user?.let {
            createStatus(StatusCode.OK, "User retrieved successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, USER_NOT_FOUND_MESSAGE)
        return GetUserResponse.newBuilder()
            .setUser(user?.let { mapToGrpcUserData(it) } ?: UserData.getDefaultInstance())
            .setStatus(status)
            .build()
    }

    override suspend fun updateUser(request: UpdateUserRequest): UpdateUserResponse {
        val updatedUser = userService.updateUser(request.userId, mapFromGrpcUser(request.user))
        val status = updatedUser?.let {
            createStatus(StatusCode.OK, "User updated successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, USER_NOT_FOUND_MESSAGE)
        return UpdateUserResponse.newBuilder()
            .setUser(updatedUser?.let { mapToGrpcUser(it).userData } ?: UserData.getDefaultInstance())
            .setStatus(status)
            .build()
    }

    override suspend fun deleteUser(request: DeleteUserRequest): DeleteUserResponse {
        val success = userService.deleteUser(request.userId)
        val status = if (success) {
            createStatus(StatusCode.OK, "User deleted successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, USER_NOT_FOUND_MESSAGE)
        }
        return DeleteUserResponse.newBuilder()
            .setUserId(request.userId)
            .setStatus(status)
            .build()
    }

    override suspend fun getUserByEmail(request: GetUserByEmailRequest): GetUserByEmailResponse {
        val user = userService.getUserByEmail(request.email)
        val status = user?.let {
            createStatus(StatusCode.OK, "User retrieved successfully")
        } ?: createStatus(StatusCode.NOT_FOUND, USER_NOT_FOUND_MESSAGE)
        return GetUserByEmailResponse.newBuilder()
            .setUser(user?.let { mapToGrpcUserData(it) } ?: UserData.getDefaultInstance())
            .setStatus(status)
            .build()
    }
}
