/**
 * Service interface for managing User entities.
 */
interface UserService {

    /**
     * Creates a new user.
     * @param user the user to create
     * @return the created user
     */
    fun createUser(user: User): User

    /**
     * Retrieves a user by their ID.
     * @param userId the ID of the user to retrieve
     * @return the user with the given ID, or null if no user found
     */
    fun getUser(userId: String): User?

    /**
     * Updates an existing user.
     * @param userId the ID of the user to update
     * @param user the user data to update
     * @return the updated user, or null if the user does not exist
     */
    fun updateUser(userId: String, user: User): User?

    /**
     * Deletes a user by their ID.
     * @param userId the ID of the user to delete
     * @return true if the user was deleted, false otherwise
     */
    fun deleteUser(userId: String): Boolean
}
