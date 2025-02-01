import java.util.*

/**
 * Common object containing utility functions for tests.
 */
object Common {

    /**
     * Creates a test user with the provided parameters or default values.
     *
     * @param id The ID of the user. Default is an uuid string.
     * @param name The name of the user. Default is "Default".
     * @param surname The surname of the user. Default is "User".
     * @param email The email of the user. Default is "random-uuid@example.com".
     * @param password The password of the user. Default is "password123".
     * @return A User object with the specified or default values.
     */
    fun createTestUser(
        id: String = UUID.randomUUID().toString(),
        name: String = "Default",
        surname: String = "User",
        email: String = UUID.randomUUID().toString() + "@example.com",
        password: String = "password123",
    ): User = User(UserData(id, name, surname, email), password)

    /**
     * Creates a test group with the provided parameters or default values.
     *
     * @param id The ID of the group. Default is an empty string.
     * @param name The name of the group. Default is "Default".
     * @param members The list of users that are members of the group. Default is an empty list.
     * @param createdBy The user who created the group. Default is a test user created by `createTestUser()`.
     * @return A Group object with the specified or default values.
     */
    fun createTestGroup(
        id: String = UUID.randomUUID().toString(),
        name: String = "Default",
        members: List<UserData> = emptyList(),
        createdBy: UserData = createTestUser().userData,
    ): Group = Group(id, name, members, createdBy)
}
