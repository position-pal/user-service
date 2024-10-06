import Common.createTestUser
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import user.UserRepository
import user.UserService
import user.UserServiceImpl

class PostgresUserRepositoryTest : FunSpec({

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

    context("saveUserSuccessfully") {
        test("should save a user and return the saved user") {
            val user = createTestUser(name = "John")

            val savedUser = userService.createUser(user)
            createdUserIds.add(savedUser.id)

            savedUser.id shouldNotBe ""
            savedUser.name shouldBe "John"
            userRepository.findById(savedUser.id) shouldNotBe null
        }
    }

    context("findByIdReturnsUser") {
        test("should return a user if it exists") {
            val user = createTestUser(name = "Jane")

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.id)
            val retrievedUser = userService.getUser(createdUser.id)

            retrievedUser shouldNotBe null
            retrievedUser?.id shouldBe createdUser.id
            retrievedUser?.name shouldBe "Jane"
        }

        test("should return null if user does not exist") {
            val nonExistentUserId = "non-existent-id"
            val retrievedUser = userService.getUser(nonExistentUserId)

            retrievedUser shouldBe null
        }
    }

    context("updateUserSuccessfully") {
        test("should update an existing user and return the updated user") {
            val user = createTestUser()

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.id)
            val updatedUser = createdUser.copy(name = "Jack")

            val result = userService.updateUser(createdUser.id, updatedUser)

            result shouldNotBe null
            result?.name shouldBe "Jack"
            userRepository.findById(createdUser.id)?.name shouldBe "Jack"
        }

        test("should return null when trying to update a non-existent user") {
            val nonExistentUserId = "non-existent-id"
            val user = createTestUser(id = nonExistentUserId)

            val result = userService.updateUser(nonExistentUserId, user)

            result shouldBe null
        }
    }

    context("deleteUserSuccessfully") {
        test("should delete an existing user and return true") {
            val user = createTestUser()

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.id)
            val deleteResult = userService.deleteUser(createdUser.id)

            deleteResult shouldBe true
            userRepository.findById(createdUser.id) shouldBe null
        }

        test("should return false when trying to delete a non-existent user") {
            val nonExistentUserId = "non-existent-id"
            val deleteResult = userService.deleteUser(nonExistentUserId)

            deleteResult shouldBe false
        }
    }

    context("findAllUsersReturnsList") {
        test("should return a list of all users") {
            val users = listOf(
                createTestUser(id = "1", name = "John"),
                createTestUser(id = "2", name = "Jane"),
            )

            users.forEach { user ->
                val createdUser = userService.createUser(user)
                createdUserIds.add(createdUser.id)
            }

            val allUsers = userRepository.findAll()
            allUsers.size shouldBe 2
        }
    }
})
