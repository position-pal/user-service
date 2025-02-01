/**
 * Data class representing a Group entity.
 *
 * @property id the unique identifier of the group
 * @property name the name of the group
 * @property members the list of usersId who are members of the group
 * @property createdBy the userId of user who created the group
 */
data class Group(
    val id: String,
    val name: String,
    val members: List<UserData>,
    val createdBy: UserData,
)
