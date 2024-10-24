package auth
import Audience
import Issuer
import Secret
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import java.util.*

/**
 * The expiration time of the JWT token in milliseconds.
 */
const val EXPIRATION_TIME: Int = 60000

/**
 * Implementation of the AuthService interface.
 *
 * @property authRepository The repository for authentication operations.
 * @property secret The secret key used for signing the JWT.
 * @property issuer The issuer of the JWT.
 * @property audience The audience of the JWT.
 */
class AuthServiceImpl(
    private val authRepository: AuthRepository,
    private val secret: Secret,
    private val issuer: Issuer,
    private val audience: Audience,
    private val expirationTime: Int = EXPIRATION_TIME,
) : AuthService {

    private val algorithm = Algorithm.HMAC256(secret.value)

    /**
     * Authenticates a user by their email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A JWT token if authentication is successful, null otherwise.
     */
    override fun authenticate(email: String, password: String): String? {
        if (!authRepository.checkCredentials(email, password)) {
            return null
        }
        return JWT.create()
            .withIssuer(issuer.value)
            .withAudience(audience.value)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationTime)) // Token expires in 1 minute
            .sign(algorithm)
    }

    /**
     * Authorizes a user by verifying their JWT token.
     *
     * @param token The JWT token to verify.
     * @return `true` if the token is valid, `false` otherwise.
     */
    override fun authorize(token: String): Boolean =
        try {
            val verifier = JWT.require(algorithm)
                .withIssuer(issuer.value)
                .withAudience(audience.value)
                .build()
            verifier.verify(token)
            true
        } catch (_: JWTVerificationException) {
            false
        }
}
