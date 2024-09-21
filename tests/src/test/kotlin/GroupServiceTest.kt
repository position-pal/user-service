import group.GroupRepository
import group.GroupService
import group.GroupServiceImpl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class GroupServiceTest : FunSpec({

    val groupRepository: GroupRepository = InMemoryGroupRepository()
    val groupService: GroupService = GroupServiceImpl(groupRepository)
    val groupCreator = User(
        id = "1",
        name = "Admin",
        surname = "User",
        email = "admin@example.com",
        password = "admin123",
        role = "admin",
    )

    context("createGroup") {
        test("createGroup should add a group and return the created group") {
            val group = Group(
                id = "",
                name = "Friends",
                members = listOf(),
                createdBy = groupCreator,
            )

            val createdGroup = groupService.createGroup(group)

            createdGroup.id shouldNotBe ""
            createdGroup.name shouldBe "Friends"
            groupRepository.findById(createdGroup.id) shouldNotBe null
        }
    }

    context("getGroup") {
        test("getGroup should return a group if it exists") {
            val group = Group(
                id = "",
                name = "Testers",
                members = listOf(),
                createdBy = groupCreator,
            )

            val createdGroup = groupService.createGroup(group)
            val retrievedGroup = groupService.getGroup(createdGroup.id)

            retrievedGroup shouldNotBe null
            retrievedGroup?.id shouldBe createdGroup.id
            retrievedGroup?.name shouldBe "Testers"
        }

        test("getGroup should return null if group does not exist") {
            val nonExistentGroupId = "non-existent-id"
            val retrievedGroup = groupService.getGroup(nonExistentGroupId)

            retrievedGroup shouldBe null
        }
    }

    context("updateGroup") {
        test("updateGroup should update an existing group and return the updated group") {
            val group = Group(
                id = "",
                name = "Basketball Team",
                members = listOf(),
                createdBy = groupCreator,
            )

            val createdGroup = groupService.createGroup(group)
            val updatedGroup = createdGroup.copy(name = "Volleyball Team")

            val result = groupService.updateGroup(createdGroup.id, updatedGroup)

            result shouldNotBe null
            result?.name shouldBe "Volleyball Team"
            groupRepository.findById(createdGroup.id)?.name shouldBe "Volleyball Team"
        }

        test("updateGroup should return null when trying to update a non-existent group") {
            val nonExistentGroupId = "non-existent-id"
            val group = Group(
                id = nonExistentGroupId,
                name = "NonExistent",
                members = listOf(),
                createdBy = groupCreator,
            )

            val result = groupService.updateGroup(nonExistentGroupId, group)

            result shouldBe null
        }
    }

    context("deleteGroup") {
        test("deleteGroup should delete an existing group and return true") {
            val group = Group(
                id = "",
                name = "Family",
                members = listOf(),
                createdBy = groupCreator,
            )

            val createdGroup = groupService.createGroup(group)
            val deleteResult = groupService.deleteGroup(createdGroup.id)

            deleteResult shouldBe true
            groupRepository.findById(createdGroup.id) shouldBe null
        }

        test("deleteGroup should return false when trying to delete a non-existent group") {
            val nonExistentGroupId = "non-existent-id"
            val deleteResult = groupService.deleteGroup(nonExistentGroupId)

            deleteResult shouldBe false
        }
    }

    context("addMember") {
        test("addMember should add a user to the group and return the updated group") {
            val group = Group(
                id = "",
                name = "Book Club",
                members = listOf(),
                createdBy = groupCreator,
            )

            val createdGroup = groupService.createGroup(group)
            val updatedGroup = groupService.addMember(createdGroup.id, "test-uuid")

            updatedGroup shouldNotBe null
            updatedGroup?.members shouldBe listOf("test-uuid")
            groupRepository.findById(createdGroup.id)?.members shouldBe listOf("test-uuid")
        }

        test("addMember should return null when trying to add a user to a non-existent group") {
            val nonExistentGroupId = "non-existent-id"
            val result = groupService.addMember(nonExistentGroupId, "test-uuid")

            result shouldBe null
        }
    }

    context("removeMember") {
        test("removeMember should remove a user from the group and return the updated group") {
            val group = Group(
                id = "",
                name = "Chess Club",
                members = listOf("test-uuid"),
                createdBy = groupCreator,
            )

            val createdGroup = groupService.createGroup(group)
            val updatedGroup = groupService.removeMember(createdGroup.id, "test-uuid")

            updatedGroup shouldNotBe null
            updatedGroup?.members shouldBe emptyList()
            groupRepository.findById(createdGroup.id)?.members shouldBe emptyList()
        }

        test("removeMember should return null when trying to remove a user from a non-existent group") {
            val nonExistentGroupId = "non-existent-id"
            val result = groupService.removeMember(nonExistentGroupId, "test-uuid")

            result shouldBe null
        }
    }
})
