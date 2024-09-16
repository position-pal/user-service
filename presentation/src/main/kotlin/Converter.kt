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
}
