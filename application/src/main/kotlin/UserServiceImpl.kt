/**
 * Implementation of the UserService interface.
 *
 * @property userRepository the repository used for managing User entities
 */
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    /**
     * Creates a new user.
     * @param user the user to create
     * @return the created user
     */
    override fun createUser(user: User): User {
        return userRepository.save(user)
    }

    /**
     * Retrieves a user by their ID.
     * @param userId the ID of the user to retrieve
     * @return the user with the given ID, or null if no user found
     */
    override fun getUser(userId: String): User? {
        return userRepository.findById(userId)
    }

    /**
     * Updates an existing user.
     * @param userId the ID of the user to update
     * @param user the user data to update
     * @return the updated user, or null if the user does not exist
     */
    override fun updateUser(userId: String, user: User): User? {
        val existingUser = userRepository.findById(userId)
        return if (existingUser != null) {
            val updatedUser = user.copy(id = userId)
            userRepository.update(updatedUser)
        } else {
            null
        }
    }

    /**
     * Deletes a user by their ID.
     * @param userId the ID of the user to delete
     * @return true if the user was deleted, false otherwise
     */
    override fun deleteUser(userId: String): Boolean {
        return userRepository.deleteById(userId)
    }
}
