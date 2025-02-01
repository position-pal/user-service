import Common.createTestUser
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

    context("createUserInstance") {
        test("createUserInstance should add a new user and return it") {
            val user = createTestUser(name = "John", surname = "Doe", email = "john.doe@example.com")

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.userData.id)

            createdUser.userData.id shouldNotBe ""
            createdUser.userData.name shouldBe "John"
            userRepository.findById(createdUser.userData.id) shouldNotBe null
        }
    }

    context("retrieveUser") {
        test("retrieveUser should return the user if it exists") {
            val user = createTestUser(id = "test-123", name = "Jane", surname = "Doe", email = "jane.doe@example.com")

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.userData.id)
            val retrievedUser = userService.getUser(createdUser.userData.id)

            retrievedUser shouldNotBe null
            retrievedUser?.userData?.id shouldBe createdUser.userData.id
            retrievedUser?.userData?.name shouldBe "Jane"
        }

        test("retrieveUser should return null if the user does not exist") {
            val nonExistentUserId = "non-existent-id"
            val retrievedUser = userService.getUser(nonExistentUserId)

            retrievedUser shouldBe null
        }
    }

    context("modifyUser") {
        test("modifyUser should update an existing user and return the updated user") {
            val user = createTestUser(name = "Mike", surname = "Smith", email = "mike.smith@example.com")

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.userData.id)
            val updatedUser = createdUser.copy(userData = createdUser.userData.copy(name = "Michael"))

            val result = userService.updateUser(createdUser.userData.id, updatedUser)

            result shouldNotBe null
            result?.userData?.name shouldBe "Michael"
            userRepository.findById(createdUser.userData.id)?.userData?.name shouldBe "Michael"
        }

        test("modifyUser should return null if the user does not exist") {
            val nonExistentUserId = "non-existent-id"
            val user = createTestUser(id = nonExistentUserId, name = "NonExistent", surname = "User")

            val result = userService.updateUser(nonExistentUserId, user)

            result shouldBe null
        }
    }

    context("removeUser") {
        test("removeUser should delete an existing user and return true") {
            val user = createTestUser(name = "Eve", surname = "Jones", email = "eve.jones@example.com")

            val createdUser = userService.createUser(user)
            createdUserIds.add(createdUser.userData.id)
            val deleteResult = userService.deleteUser(createdUser.userData.id)

            deleteResult shouldBe true
            userRepository.findById(createdUser.userData.id) shouldBe null
        }

        test("removeUser should return false when trying to delete a non-existent user") {
            val nonExistentUserId = "non-existent-id"
            val deleteResult = userService.deleteUser(nonExistentUserId)

            deleteResult shouldBe false
        }
    }
})
