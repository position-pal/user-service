package auth

/**
 * Interface representing the repository for authentication operations.
 */
fun interface AuthRepository {
    /**
     * Checks the credentials of a user.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return `true` if the credentials are valid, `false` otherwise.
     */
    fun checkCredentials(email: String, password: String): Boolean
}
