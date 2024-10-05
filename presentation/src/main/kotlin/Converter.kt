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
        return UserOuterClass.User.newBuilder()
            .setId(user.id)
            .setName(user.name)
            .setSurname(user.surname)
            .setEmail(user.email)
            .setPassword(user.password)
            .setRole(user.role)
            .build()
    }

    /**
     * Maps a gRPC User to a User entity.
     *
     * @param grpcUser the gRPC User to map
     * @return the mapped User entity
     */
    fun mapFromGrpcUser(grpcUser: UserOuterClass.User): User {
        return User(
            id = grpcUser.id,
            name = grpcUser.name,
            surname = grpcUser.surname,
            email = grpcUser.email,
            password = grpcUser.password,
            role = grpcUser.role,
        )
    }

    /**
     * Maps a Group entity to a gRPC Group.
     *
     * @param group the Group entity to map
     * @return the mapped gRPC Group
     */
    fun mapToGrpcGroup(group: Group): GroupOuterClass.Group {
        return GroupOuterClass.Group.newBuilder()
            .setId(group.id)
            .setName(group.name)
            .addAllMembers(group.members)
            .setCreatedBy(group.createdBy)
            .build()
    }

    /**
     * Maps a gRPC Group to a Group entity.
     *
     * @param grpcGroup the gRPC Group to map
     * @return the mapped Group entity
     */
    fun mapFromGrpcGroup(grpcGroup: GroupOuterClass.Group): Group {
        return Group(
            id = grpcGroup.id,
            name = grpcGroup.name,
            members = grpcGroup.membersList,
            createdBy = grpcGroup.createdBy,
        )
    }
}
