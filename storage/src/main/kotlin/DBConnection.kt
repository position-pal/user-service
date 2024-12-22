import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import java.sql.SQLException

/**
 * Singleton object for managing the database connection.
 */

object DBConnection {
    private const val DEFAULT_HOST = "localhost"
    private const val DEFAULT_PORT = 5432

    private val dotenv: Dotenv = dotenv {
        directory = "../"
        ignoreIfMissing = true
    }

    /**
     * Extension property to get the sequence of users from the database.
     */
    val Database.users get() = this.sequenceOf(Users)

    /**
     * Extension property to get the sequence of groups from the database.
     */
    val Database.groups get() = this.sequenceOf(Groups)

    /**
     * Extension property to get the sequence of memberships from the database.
     */
    val Database.memberships get() = this.sequenceOf(Memberships)

    /**
     * Gets a database object for connecting to the PostgreSQL database.
     *
     * @param username the username for the database
     * @param password the password for the database
     * @param dbName the name of the database
     * @return a database object for connecting to the PostgreSQL database
     */
    fun getDatabaseObject(
        username: String? = null,
        password: String? = null,
        dbName: String? = null,
    ): Database {
        val user = username ?: dotenv.get("POSTGRES_USER")
        val pass = password ?: dotenv.get("POSTGRES_PASSWORD")
        val db = dbName ?: dotenv.get("POSTGRES_DB")
        val host = dotenv.get("POSTGRES_HOST") ?: DEFAULT_HOST
        val port = dotenv.get("POSTGRES_PORT") ?: DEFAULT_PORT

        return runCatching {
            Database.connect(
                url = "jdbc:postgresql://$host:$port/$db",
                driver = "org.postgresql.Driver",
                user = user,
                password = pass,
            )
        }.onFailure { e ->
            throw SQLException("Error while connecting to the database", e)
        }.getOrThrow()
    }
}
