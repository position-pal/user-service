import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import user.UserRepository
import user.UserService
import user.UserServiceImpl

class UserServiceTest : FunSpec({

    val userRepository: UserRepository = InMemoryUserRepository()
    val userService: UserService = UserServiceImpl(userRepository)
    val createdUserIds = mutableListOf<String>()

    beforeEach {
        createdUserIds.clear()
    }

    afterEach {
        createdUserIds.forEach { userId ->
            userRepository.deleteById(userId)
        }
    }

    context("createUser") {
        test("createUser should add a user and return the created user") {
            val user = User(
                id = "",
                name = "John",
                surname = "Doe",
                email = "john.doe@example.com",
                password = "password123",
                role = "admin",
            )

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.id)

            createdUser.id shouldNotBe ""
            createdUser.name shouldBe "John"
            userRepository.findById(createdUser.id) shouldNotBe null
        }
    }

    context("getUser") {
        test("getUser should return a user if it exists") {
            val user = User(
                id = "test-123",
                name = "Jane",
                surname = "Doe",
                email = "jane.doe@example.com",
                password = "password123",
                role = "user",
            )

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.id)
            val retrievedUser = userService.getUser(createdUser.id)

            retrievedUser shouldNotBe null
            retrievedUser?.id shouldBe createdUser.id
            retrievedUser?.name shouldBe "Jane"
        }

        test("getUser should return null if user does not exist") {
            val nonExistentUserId = "non-existent-id"
            val retrievedUser = userService.getUser(nonExistentUserId)

            retrievedUser shouldBe null
        }
    }

    context("updateUser") {
        test("updateUser should update an existing user and return the updated user") {
            val user = User(
                id = "",
                name = "Mike",
                surname = "Smith",
                email = "mike.smith@example.com",
                password = "password123",
                role = "user",
            )

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.id)
            val updatedUser = createdUser.copy(name = "Michael")

            val result = userService.updateUser(createdUser.id, updatedUser)

            result shouldNotBe null
            result?.name shouldBe "Michael"
            userRepository.findById(createdUser.id)?.name shouldBe "Michael"
        }

        test("updateUser should return null when trying to update a non-existent user") {
            val nonExistentUserId = "non-existent-id"
            val user = User(
                id = nonExistentUserId,
                name = "NonExistent",
                surname = "User",
                email = "non.existent@example.com",
                password = "password123",
                role = "admin",
            )

            val result = userService.updateUser(nonExistentUserId, user)

            result shouldBe null
        }
    }

    context("deleteUser") {
        test("deleteUser should delete an existing user and return true") {
            val user = User(
                id = "",
                name = "Eve",
                surname = "Jones",
                email = "eve.jones@example.com",
                password = "password123",
                role = "admin",
            )

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.id)
            val deleteResult = userService.deleteUser(createdUser.id)

            deleteResult shouldBe true
            userRepository.findById(createdUser.id) shouldBe null
        }

        test("deleteUser should return false when trying to delete a non-existent user") {
            val nonExistentUserId = "non-existent-id"
            val deleteResult = userService.deleteUser(nonExistentUserId)

            deleteResult shouldBe false
        }
    }
})
