package auth

/**
 * AuthService interface provides methods for authentication and authorization.
 */
interface AuthService {

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param email the email of the user
     * @param password the password of the user
     * @return a token if authentication is successful
     */
    fun authenticate(email: String, password: String): String?

    /**
     * Authorizes a user based on the provided token.
     *
     * @param token the token to be validated
     * @return true if the token is valid, false otherwise
     */
    fun authorize(token: String): Boolean

    /**
     * Extracts the email from a given token.
     *
     * @param token the token of the user
     * @return email of user encoded in token
     */
    fun getEmailFromToken(token: String): String?
}
