import user.UserRepository

/**
 * In-memory implementation of the user.UserRepository interface.
 */
class InMemoryUserRepository : UserRepository {

    private val users = mutableMapOf<String, User>()

    /**
     * Saves a given user.
     * @param user the user to save
     * @return the saved user with a generated ID
     */
    override fun save(user: User): User {
        val userId = java.util.UUID.randomUUID().toString()
        val newUser = user.copy(id = userId)
        users[userId] = newUser
        return newUser
    }

    /**
     * Retrieves a user by their ID.
     * @param userId the ID of the user to retrieve
     * @return the user with the given ID, or null if no user found
     */
    override fun findById(userId: String): User? = users[userId]

    /**
     * Updates a given user.
     * @param user the user to update
     * @return the updated user, or null if the user does not exist
     */
    override fun update(user: User): User? =
        users.takeIf { it.containsKey(user.id) }
            ?.apply { this[user.id] = user }
            ?.let { user }

    /**
     * Deletes a user by their ID.
     * @param userId the ID of the user to delete
     * @return true if the user was deleted, false otherwise
     */
    override fun deleteById(userId: String): Boolean = users.remove(userId) != null

    /**
     * Retrieves all users.
     * @return a list of all users
     */
    override fun findAll(): List<User> = users.values.toList()
}
