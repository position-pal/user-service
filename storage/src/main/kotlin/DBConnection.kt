import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import java.sql.SQLException

/**
 * Singleton object for managing the database connection.
 */
object DBConnection {

    private const val HOST = "localhost"
    private const val PORT = 5432
    private const val TABLE = "users_and_groups"

    private val dotenv: Dotenv = dotenv {
        directory = "../"
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
     * Function to get the database object.
     *
     * @return the connected Database object
     * @throws DBConnectionException if there is an error while connecting to the database
     */
    fun getDatabaseObject(): Database {
        try {
            return Database.connect(
                url = "jdbc:postgresql://$HOST:$PORT/$TABLE",
                driver = "org.postgresql.Driver",
                user = dotenv.get("POSTGRES_USER"),
                password = dotenv.get("POSTGRES_PASSWORD"),
            )
        } catch (e: SQLException) {
            throw SQLException("Error while connecting to the database", e)
        }
    }
}
