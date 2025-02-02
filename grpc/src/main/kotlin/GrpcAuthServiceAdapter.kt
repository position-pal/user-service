import Auth.AuthenticateRequest
import Auth.AuthenticateResponse
import Auth.AuthorizeRequest
import Auth.AuthorizeResponse
import Auth.AuthorizeUserToAccessGroupRequest
import Auth.AuthorizeUserToAccessGroupResponse
import AuthServiceGrpcKt.AuthServiceCoroutineImplBase
import StatusUtility.createStatus
import UserOuterClass.StatusCode
import auth.AuthService
import group.GroupService

/**
 * Adapter class for gRPC authentication service.
 *
 * @property authService The authentication service to be used.
 */
class GrpcAuthServiceAdapter(private val authService: AuthService, private val groupService: GroupService) :
    AuthServiceCoroutineImplBase() {

    /**
     * Authenticates a user based on the provided request.
     *
     * @param request The authentication request containing username and password.
     * @return The authentication response containing the JWT token.
     */
    override suspend fun authenticate(request: AuthenticateRequest): AuthenticateResponse {
        val token = authService.authenticate(request.email, request.password)
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

    /**
     * Authorizes a user to access a group based on the provided request.
     *
     * @param request The authorization request containing the JWT token and group ID.
     * @return The authorization response indicating whether the user is authorized.
     */
    override suspend fun authorizeUserToAccessGroup(
        request: AuthorizeUserToAccessGroupRequest,
    ): AuthorizeUserToAccessGroupResponse {
        val email = authService.getEmailFromToken(request.token).orEmpty()
        groupService.findAllGroupsOfUser(email).forEach {
            if (it.id == request.groupId) {
                return AuthorizeUserToAccessGroupResponse.newBuilder()
                    .setAuthorized(true)
                    .setStatus(createStatus(StatusCode.OK, "OK"))
                    .build()
            }
        }
        return AuthorizeUserToAccessGroupResponse.newBuilder()
            .setAuthorized(false)
            .setStatus(createStatus(StatusCode.WRONG_CREDENTIALS, "Unauthorized"))
            .build()
    }
}
