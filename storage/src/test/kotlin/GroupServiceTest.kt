import group.GroupServiceImpl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk

class GroupServiceTest : FunSpec({

    val userRepository = InMemoryUserRepository()
    val groupRepository = InMemoryGroupRepository()
    val messageAdapter = mockk<MessageAdapter>()
    val groupService = GroupServiceImpl(groupRepository, messageAdapter)
    val createdGroupIds = mutableListOf<String>()
    val createdUserIds = mutableListOf<String>()

    val creator = User("test-uuid", "Test", "User", "test@email.it", "test-password", "user")

    beforeEach {
        createdGroupIds.clear()
        createdUserIds.clear()
        every { messageAdapter.postEvent(any(), any()) } returns Unit
    }

    afterEach {
        createdGroupIds.forEach(groupRepository::deleteById)
        createdUserIds.forEach(userRepository::deleteById)
    }

    beforeSpec {
        userRepository.save(User("test-uuid", "Test", "User", "test.user@example.com", "password123", "admin"))
    }

    afterSpec {
        createdGroupIds.forEach(groupRepository::deleteById)
        userRepository.deleteById("test-uuid")
    }

    context("createGroup") {
        test("should add a group and return the created group") {
            val group = Group("", "Friends", listOf(), creator)
            val createdGroup = groupService.createGroup(group)

            createdGroup.id shouldNotBe ""
            createdGroup.name shouldBe "Friends"
            groupRepository.findById(createdGroup.id) shouldNotBe null
        }
    }

    context("getGroup") {
        test("should return a group if it exists") {
            val group = Group("", "Testers", listOf(), creator)
            val createdGroup = groupService.createGroup(group)
            val retrievedGroup = groupService.getGroup(createdGroup.id)

            retrievedGroup shouldNotBe null
            retrievedGroup?.id shouldBe createdGroup.id
            retrievedGroup?.name shouldBe "Testers"
        }

        test("should return null if group does not exist") {
            groupService.getGroup("non-existent-id") shouldBe null
        }
    }

    context("updateGroup") {
        test("should update an existing group and return the updated group") {
            val group = Group("", "Basketball Team", listOf(), creator)
            val createdGroup = groupService.createGroup(group)
            val updatedGroup = createdGroup.copy(name = "Volleyball Team")

            val result = groupService.updateGroup(createdGroup.id, updatedGroup)

            result shouldNotBe null
            result?.name shouldBe "Volleyball Team"
            groupRepository.findById(createdGroup.id)?.name shouldBe "Volleyball Team"
        }

        test("should return null when trying to update a non-existent group") {
            val group = Group("non-existent-id", "NonExistent", listOf(), creator)
            groupService.updateGroup("non-existent-id", group) shouldBe null
        }
    }

    context("deleteGroup") {
        test("should delete an existing group and return true") {
            val group = Group("", "Family", listOf(), creator)
            val createdGroup = groupService.createGroup(group)
            groupService.deleteGroup(createdGroup.id) shouldBe true
            groupRepository.findById(createdGroup.id) shouldBe null
        }

        test("should return false when trying to delete a non-existent group") {
            groupService.deleteGroup("non-existent-id") shouldBe false
        }
    }

    context("addMember") {
        val user = User("new-user-id", "New", "User", "new.user@example.com", "password123", "user")
        test("should create a user, add it to the group, and return the updated group") {
            val group = Group("", "Book Club", listOf(), creator)
            val createdGroup = groupService.createGroup(group)
            createdGroupIds.add(createdGroup.id)

            userRepository.save(user)
            createdUserIds.add(user.id)

            val updatedGroup = groupService.addMember(createdGroup.id, user)

            updatedGroup shouldNotBe null
            updatedGroup?.members shouldBe listOf(user)
            groupRepository.findById(createdGroup.id)?.members shouldBe listOf(user)
        }

        test("should return null when trying to add a user to a non-existent group") {
            groupService.addMember("non-existent-id", user) shouldBe null
        }
    }

    context("removeMember") {
        val user = User("test-uuid", "Test", "User", "test.user@example.com", "password123", "user")
        test("should remove a user from the group and delete the user, then return the updated group") {
            val group = Group("", "Chess Club", listOf(user), user)
            val createdGroup = groupService.createGroup(group)
            createdGroupIds.add(createdGroup.id)

            userRepository.save(user)
            createdUserIds.add(user.id)

            val updatedGroup = groupService.removeMember(createdGroup.id, user)

            updatedGroup shouldNotBe null
            updatedGroup?.members shouldBe emptyList()
            groupRepository.findById(createdGroup.id)?.members shouldBe emptyList()
        }

        test("should return null when trying to remove a user from a non-existent group") {
            groupService.removeMember("non-existent-id", user) shouldBe null
        }
    }
})
