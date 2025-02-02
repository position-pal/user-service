import Auth.AuthenticateRequest
import Auth.AuthorizeRequest
import Auth.AuthorizeUserToAccessGroupRequest
import UserOuterClass.StatusCode
import auth.AuthService
import group.GroupService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class GrpcAuthServiceTest : FunSpec({

    val mockkAuthService = mockk<AuthService>()
    val mockkGroupService = mockk<GroupService>()
    val grpcAdapter = GrpcAuthServiceAdapter(mockkAuthService, mockkGroupService)

    context("authenticate") {
        test("should return token when authentication is successful") {
            val request = AuthenticateRequest.newBuilder().setEmail("user").setPassword("pass").build()
            coEvery { mockkAuthService.authenticate("user", "pass") } returns "token"

            val response = grpcAdapter.authenticate(request)

            response.token shouldBe "token"
        }

        test("should return empty token when authentication fails") {
            val request = AuthenticateRequest.newBuilder().setEmail("user").setPassword("wrongpass").build()
            coEvery { mockkAuthService.authenticate("user", "wrongpass") } returns ""

            val response = grpcAdapter.authenticate(request)

            response.token shouldBe ""
        }
    }

    context("authorize") {
        test("should return authorized status when token is valid") {
            val request = AuthorizeRequest.newBuilder().setToken("validToken").build()
            coEvery { mockkAuthService.authorize("validToken") } returns true

            val response = grpcAdapter.authorize(request)

            response.authorized shouldBe true
            response.status.code shouldBe StatusCode.OK
        }

        test("should return unauthorized status when token is invalid") {
            val request = AuthorizeRequest.newBuilder().setToken("invalidToken").build()
            coEvery { mockkAuthService.authorize("invalidToken") } returns false

            val response = grpcAdapter.authorize(request)

            response.authorized shouldBe false
            response.status.code shouldBe StatusCode.WRONG_CREDENTIALS
        }
    }

    fun mockValidUserGroups(email: String) {
        coEvery { mockkGroupService.findAllGroupsOfUser(email) } returns listOf(
            Group(
                id = "group1",
                createdBy = UserData("test_id", "name", "test surname", "admin@example.com"),
                members = listOf(UserData("user_id1", "User", "One", email)),
                name = "Test Group 1",
            ),
            Group(
                id = "group2",
                createdBy = UserData("test_id2", "admin", "user", "admin2@example.com"),
                members = listOf(UserData("user_id2", "User", "Two", "user2@example.com")),
                name = "Test Group 2",
            ),
        )
    }

    context("authorizeUserToAccessGroup") {
        test("should return authorized when user is in the group") {
            val request = AuthorizeUserToAccessGroupRequest.newBuilder()
                .setToken("validToken")
                .setGroupId("group1")
                .build()

            coEvery { mockkAuthService.getEmailFromToken("validToken") } returns "user@example.com"
            mockValidUserGroups("user@example.com")

            val response = grpcAdapter.authorizeUserToAccessGroup(request)

            response.authorized shouldBe true
            response.status.code shouldBe StatusCode.OK
        }

        test("should return unauthorized when token is invalid") {
            val request = AuthorizeUserToAccessGroupRequest.newBuilder()
                .setToken("invalidToken")
                .setGroupId("group1")
                .build()

            coEvery { mockkAuthService.getEmailFromToken("invalidToken") } returns null
            coEvery { mockkGroupService.findAllGroupsOfUser(any()) } returns emptyList()

            val response = grpcAdapter.authorizeUserToAccessGroup(request)

            response.authorized shouldBe false
            response.status.code shouldBe StatusCode.WRONG_CREDENTIALS
        }
    }
})
