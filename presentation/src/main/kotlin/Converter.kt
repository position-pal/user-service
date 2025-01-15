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
            .setId(user.id)
            .setName(user.name)
            .setSurname(user.surname)
            .setEmail(user.email)
            .setRole(user.role)
            .build()
        return UserOuterClass.User.newBuilder().setUserData(userData).setPassword(user.password).build()
    }

    /**
     * Maps a gRPC User to a User entity.
     *
     * @param grpcUser the gRPC User to map
     * @return the mapped User entity
     */
    fun mapFromGrpcUser(grpcUser: UserOuterClass.User): User = User(
        id = grpcUser.userData.id,
        name = grpcUser.userData.name,
        surname = grpcUser.userData.surname,
        email = grpcUser.userData.email,
        password = grpcUser.password,
        role = grpcUser.userData.role,
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
        .addAllMembers(group.members.map { mapToGrpcUser(it) })
        .setCreatedBy(mapToGrpcUser(group.createdBy))
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
        members = grpcGroup.membersList.map { mapFromGrpcUser(it) },
        createdBy = mapFromGrpcUser(grpcGroup.createdBy),
    )
}
