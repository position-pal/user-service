import Auth.AuthenticateRequest
import Auth.AuthorizeRequest
import UserOuterClass.StatusCode
import auth.AuthService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class GrpcAuthServiceTest : FunSpec({

    val mockkAuthService = mockk<AuthService>()
    val grpcAdapter = GrpcAuthServiceAdapter(mockkAuthService)

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
})
