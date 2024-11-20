import auth.AuthServiceImpl
import group.GroupServiceImpl
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import io.github.positionpal.AvroSerializer
import io.grpc.Server
import io.grpc.ServerBuilder
import user.UserServiceImpl

/**
 * The port number for the gRPC server.
 */
const val PORT = 8080

/**
 * Dotenv config.
 */
private val dotenv: Dotenv = dotenv {
    directory = "../"
    ignoreIfMissing = true
}

/**
 * The main entry point of the application.
 * This function sets up and starts the gRPC server with the necessary service adapters.
 */
fun main() {
    // Initialize the message adapter with Avro serialization
    val messageAdapter = RabbitMQMessageAdapter(serializer = AvroSerializer())

    // Initialize the authentication service adapter with the necessary dependencies
    val authAdapter = GrpcAuthServiceAdapter(
        AuthServiceImpl(
            PostgresAuthRepository(),
            Secret(dotenv.get("JWT_SECRET")),
            Issuer("io.github.positionpal"),
            Audience("positionpal.io"),
        ),
    )

    // Initialize the group service adapter with the necessary dependencies
    val groupAdapter = GrpcGroupServiceAdapter(
        GroupServiceImpl(
            PostgresGroupRepository(),
            messageAdapter,
        ),
    )

    // Initialize the user service adapter with the necessary dependencies
    val userAdapter = GrpcUserServiceAdapter(
        UserServiceImpl(
            PostgresUserRepository(),
        ),
    )

    // Build the gRPC server and add the service adapters
    val server: Server = ServerBuilder.forPort(PORT)
        .addService(authAdapter)
        .addService(groupAdapter)
        .addService(userAdapter)
        .build()

    // Start the server
    server.start()
    println("Server started on port 8080")

    // Keep the server running
    server.awaitTermination()
}
