/**
 * Data class representing a User entity.
 *
 * @property id the unique identifier of the user
 * @property name the first name of the user
 * @property surname the last name of the user
 * @property email the email address of the user
 * @property password the password of the user
 * @property role the role of the user in the system
 */
data class User(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: String,
)
