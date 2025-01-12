import Auth.AuthenticateRequest
import Auth.AuthenticateResponse
import Auth.AuthorizeRequest
import Auth.AuthorizeResponse
import AuthServiceGrpcKt.AuthServiceCoroutineImplBase
import StatusUtility.createStatus
import UserOuterClass.StatusCode
import auth.AuthService

/**
 * Adapter class for gRPC authentication service.
 *
 * @property authService The authentication service to be used.
 */
class GrpcAuthServiceAdapter(private val authService: AuthService) : AuthServiceCoroutineImplBase() {

    /**
     * Authenticates a user based on the provided request.
     *
     * @param request The authentication request containing username and password.
     * @return The authentication response containing the JWT token.
     */
    override suspend fun authenticate(request: AuthenticateRequest): AuthenticateResponse {
        val token = authService.authenticate(request.username, request.password)
        return AuthenticateResponse.newBuilder()
            .setToken(token)
            .setStatus(
                if (token != null) {
                    createStatus(StatusCode.OK, "OK")
                } else {
                    createStatus(StatusCode.WRONG_CREDENTIALS, "Unauthorized")
                },
            )
            .build()
    }

    /**
     * Authorizes a user based on the provided request.
     *
     * @param request The authorization request containing the JWT token.
     * @return The authorization response indicating whether the user is authorized.
     */
    override suspend fun authorize(request: AuthorizeRequest): AuthorizeResponse {
        val token = request.token.removePrefix("Bearer ").trim()
        val isAuthorized = authService.authorize(token)
        return AuthorizeResponse.newBuilder()
            .setAuthorized(isAuthorized)
            .setStatus(
                if (isAuthorized) {
                    createStatus(StatusCode.OK, "OK")
                } else {
                    createStatus(StatusCode.WRONG_CREDENTIALS, "Unauthorized")
                },
            )
            .build()
    }
}
