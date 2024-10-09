/**
 * Common object containing utility functions for tests.
 */
object Common {

    /**
     * Creates a test user with the provided parameters or default values.
     *
     * @param id The ID of the user. Default is an empty string.
     * @param name The name of the user. Default is "Default".
     * @param surname The surname of the user. Default is "User".
     * @param email The email of the user. Default is "default.user@example.com".
     * @param password The password of the user. Default is "password123".
     * @param role The role of the user. Default is "user".
     * @return A User object with the specified or default values.
     */
    fun createTestUser(
        id: String = "",
        name: String = "Default",
        surname: String = "User",
        email: String = "default.user@example.com",
        password: String = "password123",
        role: String = "user",
    ): User {
        return User(id, name, surname, email, password, role)
    }

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
        id: String = "",
        name: String = "Default",
        members: List<User> = emptyList(),
        createdBy: User = createTestUser(),
    ): Group {
        return Group(id, name, members, createdBy)
    }
}
