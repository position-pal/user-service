/**
 * Data class representing a Membership entity.
 *
 * @property userId the unique identifier of the user
 * @property groupId the unique identifier of the group
 */
data class Membership(
    val userId: String,
    val groupId: String,
)
