/**
 * Repository interface for managing User entities.
 */
interface UserRepository {

    /**
     * Saves a given user.
     * @param user the user to save
     * @return the saved user
     */
    fun save(user: User): User

    /**
     * Retrieves a user by their ID.
     * @param userId the ID of the user to retrieve
     * @return the user with the given ID, or null if no user found
     */
    fun findById(userId: String): User?

    /**
     * Updates a given user.
     * @param user the user to update
     * @return the updated user, or null if the user does not exist
     */
    fun update(user: User): User?

    /**
     * Deletes a user by their ID.
     * @param userId the ID of the user to delete
     * @return true if the user was deleted, false otherwise
     */
    fun deleteById(userId: String): Boolean

    /**
     * Retrieves all users.
     * @return a list of all users
     */
    fun findAll(): List<User>
}
