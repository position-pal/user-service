import Common.createTestGroup
import Common.createTestUser
import group.GroupRepository
import group.GroupService
import group.GroupServiceImpl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import user.UserService
import user.UserServiceImpl

class PostgresGroupRepositoryTest : FunSpec({

    val groupRepository: GroupRepository = PostgresGroupRepository()
    val groupService: GroupService = GroupServiceImpl(groupRepository)
    val userService: UserService = UserServiceImpl(PostgresUserRepository())

    context("saveGroupSuccessfully") {
        test("should save a group and return the saved group") {
            val group = createTestGroup(name = "Group1")

            val savedGroup = groupService.createGroup(group)

            savedGroup.id shouldNotBe ""
            savedGroup.name shouldBe "Group1"
            groupRepository.findById(savedGroup.id) shouldNotBe null
        }
    }

    context("findByIdReturnsGroup") {
        test("should return a group if it exists") {
            val group = createTestGroup(name = "Group2")

            val createdGroup = groupService.createGroup(group)
            val retrievedGroup = groupService.getGroup(createdGroup.id)

            retrievedGroup shouldNotBe null
            retrievedGroup?.id shouldBe createdGroup.id
            retrievedGroup?.name shouldBe "Group2"
        }

        test("should return null if group does not exist") {
            val nonExistentGroupId = "non-existent-id"
            val retrievedGroup = groupService.getGroup(nonExistentGroupId)

            retrievedGroup shouldBe null
        }
    }

    context("updateGroupSuccessfully") {
        test("should update an existing group and return the updated group") {
            val group = createTestGroup(name = "Group3")

            val createdGroup = groupService.createGroup(group)
            val updatedGroup = createdGroup.copy(name = "UpdatedGroup3")

            val result = groupService.updateGroup(createdGroup.id, updatedGroup)

            result shouldNotBe null
            result?.name shouldBe "UpdatedGroup3"
            groupRepository.findById(createdGroup.id)?.name shouldBe "UpdatedGroup3"
        }

        test("should return null when trying to update a non-existent group") {
            val nonExistentGroupId = "non-existent-id"
            val group = createTestGroup(id = nonExistentGroupId)

            val result = groupService.updateGroup(nonExistentGroupId, group)

            result shouldBe null
        }
    }

    context("deleteGroupSuccessfully") {
        test("should delete an existing group and return true") {
            val group = createTestGroup(name = "Group4")

            val createdGroup = groupService.createGroup(group)
            val deleteResult = groupService.deleteGroup(createdGroup.id)

            deleteResult shouldBe true
            groupRepository.findById(createdGroup.id) shouldBe null
        }

        test("should return false when trying to delete a non-existent group") {
            val nonExistentGroupId = "non-existent-id"
            val deleteResult = groupService.deleteGroup(nonExistentGroupId)

            deleteResult shouldBe false
        }
    }

    context("findAllGroupsReturnsList") {
        test("should return a list of all groups") {
            val groups = listOf(
                createTestGroup(id = "1", name = "Group1"),
                createTestGroup(id = "2", name = "Group2"),
            )

            groups.forEach { group ->
                groupService.createGroup(group)
            }

            val allGroups = groupRepository.findAll()
            allGroups.size shouldBe 5
        }
    }

    context("addMemberToDB") {
        val user = createTestUser(id = "test-id-to-add", name = "Johnny", email = "add-me@mail.com")
        userService.createUser(user)
        test("should add a member to an existing group and return the updated group") {
            val group = createTestGroup(name = "Group5")
            val createdGroup = groupService.createGroup(group)

            val updatedGroup = groupService.addMember(createdGroup.id, user)

            updatedGroup shouldNotBe null
            updatedGroup?.members?.contains(user) shouldBe true
        }

        test("should return null when trying to add a member to a non-existent group") {
            val nonExistentGroupId = "non-existent-id"

            val result = groupService.addMember(nonExistentGroupId, user)

            result shouldBe null
        }
    }

    context("removeMemberToDB") {
        val user = createTestUser(id = "test-id-to-remove", name = "Johnny", email = "remove-me@mail.com")
        userService.createUser(user)

        test("should remove a member from an existing group and return the updated group") {
            val group = createTestGroup(name = "Group6")
            val createdGroup = groupService.createGroup(group)
            groupService.addMember(createdGroup.id, user)

            val updatedGroup = groupService.removeMember(createdGroup.id, user)

            updatedGroup shouldNotBe null
            updatedGroup?.members?.contains(user) shouldBe false
        }

        test("should return null when trying to remove a member from a non-existent group") {
            val nonExistentGroupId = "non-existent-id"

            val result = groupService.removeMember(nonExistentGroupId, user)

            result shouldBe null
        }
    }
})
