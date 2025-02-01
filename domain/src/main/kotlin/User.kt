/**
 * Data class representing a User entity.
 *
 * @property userData the user data
 * @property password the password of the user
 */
data class User(
    val userData: UserData,
    val password: String,
)

/**
 * Data class representing user data.
 *
 * @property id the unique identifier of the user
 * @property name the name of the user
 * @property surname the surname of the user
 * @property email the email of the user
 */
data class UserData(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
)
