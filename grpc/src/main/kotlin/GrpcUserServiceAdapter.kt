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
import UserServiceGrpc.UserServiceImplBase
import io.grpc.stub.StreamObserver

/**
 * Adapter class for gRPC UserService, implementing the UserServiceImplBase.
 *
 * @property userService the service used for managing User entities
 */
class GrpcUserServiceAdapter(private val userService: UserService) : UserServiceImplBase() {

    /**
     * Creates a new user.
     * @param request the gRPC request containing user details
     * @param responseObserver the gRPC response observer
     */
    override fun createUser(request: CreateUserRequest?, responseObserver: StreamObserver<CreateUserResponse>?) {
        val user = User(
            id = "",
            name = request?.user?.name.orEmpty(),
            surname = request?.user?.surname.orEmpty(),
            email = request?.user?.email.orEmpty(),
            password = request?.user?.password.orEmpty(),
            role = request?.user?.role.orEmpty(),
        )

        val createdUser = userService.createUser(user)
        val response = CreateUserResponse.newBuilder()
            .setUser(mapToGrpcUser(createdUser))
            .setStatus(createStatus(StatusCode.OK, "User created successfully"))
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    /**
     * Retrieves a user by their ID.
     * @param request the gRPC request containing the user ID
     * @param responseObserver the gRPC response observer
     */
    override fun getUser(request: GetUserRequest?, responseObserver: StreamObserver<GetUserResponse>?) {
        val user = request?.let { userService.getUser(it.userId) }
        val status = if (user != null) {
            createStatus(StatusCode.OK, "User retrieved successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, "User not found")
        }
        val response = GetUserResponse.newBuilder()
            .setUser(user?.let { mapToGrpcUser(it) } ?: UserOuterClass.User.getDefaultInstance())
            .setStatus(status)
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    /**
     * Updates an existing user.
     * @param request the gRPC request containing user details
     * @param responseObserver the gRPC response observer
     */
    override fun updateUser(request: UpdateUserRequest?, responseObserver: StreamObserver<UpdateUserResponse>?) {
        val updatedUser = request?.let { mapFromGrpcUser(it.user) }?.let { userService.updateUser(request.userId, it) }
        val status = if (updatedUser != null) {
            createStatus(StatusCode.OK, "User updated successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, "User not found")
        }
        val response = UpdateUserResponse.newBuilder()
            .setUser(updatedUser?.let { mapToGrpcUser(it) } ?: UserOuterClass.User.getDefaultInstance())
            .setStatus(status)
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    /**
     * Deletes a user by their ID.
     * @param request the gRPC request containing the user ID
     * @param responseObserver the gRPC response observer
     */
    override fun deleteUser(request: DeleteUserRequest?, responseObserver: StreamObserver<DeleteUserResponse>?) {
        val success = request?.let { userService.deleteUser(it.userId) } ?: false
        val status = if (success) {
            createStatus(StatusCode.OK, "User deleted successfully")
        } else {
            createStatus(StatusCode.NOT_FOUND, "User not found")
        }
        val response = DeleteUserResponse.newBuilder()
            .setUserId(request?.userId.orEmpty())
            .setStatus(status)
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }
}
