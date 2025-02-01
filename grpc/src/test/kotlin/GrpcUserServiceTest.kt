import UserOuterClass.CreateUserRequest
import UserOuterClass.DeleteUserRequest
import UserOuterClass.GetUserRequest
import UserOuterClass.StatusCode
import UserOuterClass.UpdateUserRequest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import user.UserService

class GrpcUserServiceTest : FunSpec({

    val mockUserService = mockk<UserService>()
    val grpcAdapter = GrpcUserServiceAdapter(mockUserService)

    context("createUser") {
        test("should create a user and return success status") {
            val userData = UserOuterClass.UserData.newBuilder()
                .setId("123")
                .setName("Alice")
                .setSurname("Wonderland")
                .setEmail("alice@example.com")
                .build()
            val grpcUser = UserOuterClass.User.newBuilder()
                .setUserData(userData)
                .setPassword("password123")
                .build()

            val request = CreateUserRequest.newBuilder().setUser(grpcUser).build()

            val createdUser = User(
                UserData(
                    id = "123",
                    name = "Alice",
                    surname = "Wonderland",
                    email = "alice@example.com",
                ),
                password = "password123",
            )

            coEvery { mockUserService.createUser(any()) } returns createdUser

            val response = runBlocking { grpcAdapter.createUser(request) }

            response.status.code shouldBe StatusCode.OK
            response.user.name shouldBe "Alice"
        }
    }

    context("getUser") {
        test("should return a user if it exists") {
            val retrievedUser = User(
                UserData(
                    id = "123",
                    name = "Alice",
                    surname = "Wonderland",
                    email = "alice@example.com",
                ),
                password = "password123",
            )

            coEvery { mockUserService.getUser("123") } returns retrievedUser

            val request = GetUserRequest.newBuilder().setUserId("123").build()

            val response = runBlocking { grpcAdapter.getUser(request) }

            response.status.code shouldBe StatusCode.OK
            response.user.name shouldBe "Alice"
        }

        test("should return NOT_FOUND if user does not exist") {
            coEvery { mockUserService.getUser("non-existent-id") } returns null

            val request = GetUserRequest.newBuilder().setUserId("non-existent-id").build()

            val response = runBlocking { grpcAdapter.getUser(request) }

            response.status.code shouldBe StatusCode.NOT_FOUND
            response.user.name shouldBe ""
        }
    }

    context("updateUser") {
        test("should update a user and return success status") {
            val userData = UserOuterClass.UserData.newBuilder()
                .setId("123")
                .setName("Mary")
                .setSurname("Wonderland")
                .setEmail("alice@example.com")
                .build()
            val grpcUser = UserOuterClass.User.newBuilder()
                .setUserData(userData)
                .setPassword("newpassword")
                .build()

            val request = UpdateUserRequest.newBuilder()
                .setUserId("123")
                .setUser(grpcUser)
                .build()

            val updatedUser = User(
                UserData(
                    id = "123",
                    name = "Alice",
                    surname = "Wonderland",
                    email = "alice@example.com",
                ),
                password = "newpassword",
            )

            coEvery { mockUserService.updateUser("123", any()) } returns updatedUser

            val response = runBlocking { grpcAdapter.updateUser(request) }

            response.status.code shouldBe StatusCode.OK
            response.user.name shouldBe "Alice"
        }

        test("should return NOT_FOUND if user does not exist") {
            coEvery { mockUserService.updateUser("non-existent-id", any()) } returns null
            val userData = UserOuterClass.UserData.newBuilder()
                .setId("non-existent-id")
                .setName("NonExistent")
                .setSurname("User")
                .setEmail("non.existent@example.com")
                .build()
            val grpcUser = UserOuterClass.User.newBuilder()
                .setUserData(userData)
                .setPassword("password123")
                .build()

            val request = UpdateUserRequest.newBuilder()
                .setUserId("non-existent-id")
                .setUser(grpcUser)
                .build()

            val response = runBlocking { grpcAdapter.updateUser(request) }

            response.status.code shouldBe StatusCode.NOT_FOUND
            response.user.name shouldBe ""
        }
    }

    context("deleteUser") {
        test("should delete a user and return success status") {
            coEvery { mockUserService.deleteUser("123") } returns true

            val request = DeleteUserRequest.newBuilder().setUserId("123").build()

            val response = runBlocking { grpcAdapter.deleteUser(request) }

            response.status.code shouldBe StatusCode.OK
            response.userId shouldBe "123"
        }

        test("should return NOT_FOUND if user does not exist") {
            coEvery { mockUserService.deleteUser("non-existent-id") } returns false

            val request = DeleteUserRequest.newBuilder().setUserId("non-existent-id").build()

            val response = runBlocking { grpcAdapter.deleteUser(request) }

            response.status.code shouldBe StatusCode.NOT_FOUND
            response.userId shouldBe "non-existent-id"
        }
    }
})
