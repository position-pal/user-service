/**
 * Object containing methods to convert between User and UserOuterClass.User.
 */
object Converter {

    /**
     * Maps a User entity to a gRPC User.
     *
     * @param user the User entity to map
     * @return the mapped gRPC User
     */
    fun mapToGrpcUser(user: User): UserOuterClass.User {
        val userData = UserOuterClass.UserData.newBuilder()
            .setId(user.userData.id)
            .setName(user.userData.name)
            .setSurname(user.userData.surname)
            .setEmail(user.userData.email)
            .build()
        return UserOuterClass.User.newBuilder().setUserData(userData).setPassword(user.password).build()
    }

    /**
     * Mps a User entity to a gRPC UserData.
     *
     * @param userData the UserData entity to map
     * @return the mapped gRPC UserData
     */
    fun mapToGrpcUserData(userData: UserData): UserOuterClass.UserData = UserOuterClass.UserData.newBuilder()
        .setId(userData.id)
        .setName(userData.name)
        .setSurname(userData.surname)
        .setEmail(userData.email)
        .build()

    /**
     * Maps a gRPC User to a User entity.
     *
     * @param grpcUser the gRPC User to map
     * @return the mapped User entity
     */
    fun mapFromGrpcUser(grpcUser: UserOuterClass.User): User =
        User(
            UserData(
                id = grpcUser.userData.id,
                name = grpcUser.userData.name,
                surname = grpcUser.userData.surname,
                email = grpcUser.userData.email,
            ),
            password = grpcUser.password,
        )

    /**
     * Maps a gRPC UserData to a UserData entity.
     *
     * @param grpcUserData the gRPC UserData to map
     * @return the mapped UserData entity
     */
    fun mapFromGrpcUserData(grpcUserData: UserOuterClass.UserData): UserData = UserData(
        id = grpcUserData.id,
        name = grpcUserData.name,
        surname = grpcUserData.surname,
        email = grpcUserData.email,
    )

    /**
     * Maps a Group entity to a gRPC Group.
     *
     * @param group the Group entity to map
     * @return the mapped gRPC Group
     */
    fun mapToGrpcGroup(group: Group): GroupOuterClass.Group = GroupOuterClass.Group
        .newBuilder()
        .setId(group.id)
        .setName(group.name)
        .addAllMembers(group.members.map { mapToGrpcUserData(it) })
        .setCreatedBy(mapToGrpcUserData(group.createdBy))
        .build()

    /**
     * Maps a gRPC Group to a Group entity.
     *
     * @param grpcGroup the gRPC Group to map
     * @return the mapped Group entity
     */
    fun mapFromGrpcGroup(grpcGroup: GroupOuterClass.Group): Group = Group(
        id = grpcGroup.id,
        name = grpcGroup.name,
        members = grpcGroup.membersList.map { mapFromGrpcUserData(it) },
        createdBy = mapFromGrpcUserData(grpcGroup.createdBy),
    )
}
