import UserOuterClass.CreateUserRequest
import UserOuterClass.CreateUserResponse
import UserOuterClass.DeleteUserRequest
import UserOuterClass.DeleteUserResponse
import UserOuterClass.GetUserRequest
import UserOuterClass.GetUserResponse
import UserOuterClass.StatusCode
import UserOuterClass.UpdateUserRequest
import UserOuterClass.UpdateUserResponse
import io.grpc.stub.StreamObserver
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify

class GrpcUserServiceTest : FunSpec({

    val mockUserService = mockk<UserService>()
    val grpcAdapter = GrpcUserServiceAdapter(mockUserService)

    context("createUser") {
        test("should create a user and return success status") {
            val grpcUser = UserOuterClass.User.newBuilder()
                .setName("Alice")
                .setSurname("Wonderland")
                .setEmail("alice@example.com")
                .setPassword("password123")
                .setRole("admin")
                .build()

            val request = CreateUserRequest.newBuilder().setUser(grpcUser).build()

            val createdUser = User(
                id = "123",
                name = "Alice",
                surname = "Wonderland",
                email = "alice@example.com",
                password = "password123",
                role = "admin",
            )

            every { mockUserService.createUser(any()) } returns createdUser

            val responseObserver = mockk<StreamObserver<CreateUserResponse>>(relaxed = true)
            grpcAdapter.createUser(request, responseObserver)

            val slot = slot<CreateUserResponse>()
            verify { responseObserver.onNext(capture(slot)) }

            val response = slot.captured
            response.status.code shouldBe StatusCode.OK
            response.user.name shouldBe "Alice"
            verify { responseObserver.onCompleted() }
        }
    }

    context("getUser") {
        test("should return a user if it exists") {
            val retrievedUser = User(
                id = "123",
                name = "Alice",
                surname = "Wonderland",
                email = "alice@example.com",
                password = "password123",
                role = "admin",
            )

            every { mockUserService.getUser("123") } returns retrievedUser

            val request = GetUserRequest.newBuilder().setUserId("123").build()

            val responseObserver = mockk<StreamObserver<GetUserResponse>>(relaxed = true)
            grpcAdapter.getUser(request, responseObserver)

            val slot = slot<GetUserResponse>()
            verify { responseObserver.onNext(capture(slot)) }

            val response = slot.captured
            response.status.code shouldBe StatusCode.OK
            response.user.name shouldBe "Alice"
            verify { responseObserver.onCompleted() }
        }

        test("should return NOT_FOUND if user does not exist") {
            every { mockUserService.getUser("non-existent-id") } returns null

            val request = GetUserRequest.newBuilder().setUserId("non-existent-id").build()
            val responseObserver = mockk<StreamObserver<GetUserResponse>>(relaxed = true)
            grpcAdapter.getUser(request, responseObserver)

            val slot = slot<GetUserResponse>()
            verify { responseObserver.onNext(capture(slot)) }

            val response = slot.captured
            response.status.code shouldBe StatusCode.NOT_FOUND
            response.user.name shouldBe ""
            verify { responseObserver.onCompleted() }
        }
    }

    context("updateUser") {
        test("should update a user and return success status") {
            val grpcUser = UserOuterClass.User.newBuilder()
                .setId("123")
                .setName("Alice")
                .setSurname("Wonderland")
                .setEmail("alice@example.com")
                .setPassword("newpassword")
                .setRole("admin")
                .build()

            val request = UpdateUserRequest.newBuilder()
                .setUserId("123")
                .setUser(grpcUser)
                .build()

            val updatedUser = User(
                id = "123",
                name = "Alice",
                surname = "Wonderland",
                email = "alice@example.com",
                password = "newpassword",
                role = "admin",
            )

            every { mockUserService.updateUser("123", any()) } returns updatedUser

            val responseObserver = mockk<StreamObserver<UpdateUserResponse>>(relaxed = true)
            grpcAdapter.updateUser(request, responseObserver)

            val slot = slot<UpdateUserResponse>()
            verify { responseObserver.onNext(capture(slot)) }

            val response = slot.captured
            response.status.code shouldBe StatusCode.OK
            response.user.password shouldBe "newpassword"
            verify { responseObserver.onCompleted() }
        }

        test("should return NOT_FOUND if user does not exist") {
            every { mockUserService.updateUser("non-existent-id", any()) } returns null

            val grpcUser = UserOuterClass.User.newBuilder()
                .setId("non-existent-id")
                .setName("NonExistent")
                .setSurname("User")
                .setEmail("non.existent@example.com")
                .setPassword("password123")
                .setRole("admin")
                .build()

            val request = UpdateUserRequest.newBuilder()
                .setUserId("non-existent-id")
                .setUser(grpcUser)
                .build()

            val responseObserver = mockk<StreamObserver<UpdateUserResponse>>(relaxed = true)
            grpcAdapter.updateUser(request, responseObserver)

            val slot = slot<UpdateUserResponse>()
            verify { responseObserver.onNext(capture(slot)) }

            val response = slot.captured
            response.status.code shouldBe StatusCode.NOT_FOUND
            response.user.name shouldBe ""
            verify { responseObserver.onCompleted() }
        }
    }

    context("deleteUser") {
        test("should delete a user and return success status") {
            every { mockUserService.deleteUser("123") } returns true

            val request = DeleteUserRequest.newBuilder().setUserId("123").build()

            val responseObserver = mockk<StreamObserver<DeleteUserResponse>>(relaxed = true)
            grpcAdapter.deleteUser(request, responseObserver)

            val slot = slot<DeleteUserResponse>()
            verify { responseObserver.onNext(capture(slot)) }

            val response = slot.captured
            response.status.code shouldBe StatusCode.OK
            response.userId shouldBe "123"
            verify { responseObserver.onCompleted() }
        }

        test("should return NOT_FOUND if user does not exist") {
            every { mockUserService.deleteUser("non-existent-id") } returns false

            val request = DeleteUserRequest.newBuilder().setUserId("non-existent-id").build()

            val responseObserver = mockk<StreamObserver<DeleteUserResponse>>(relaxed = true)
            grpcAdapter.deleteUser(request, responseObserver)

            val slot = slot<DeleteUserResponse>()
            verify { responseObserver.onNext(capture(slot)) }

            val response = slot.captured
            response.status.code shouldBe StatusCode.NOT_FOUND
            response.userId shouldBe "non-existent-id"
            verify { responseObserver.onCompleted() }
        }
    }
})
