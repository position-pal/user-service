import UserOuterClass.Status
import UserOuterClass.StatusCode

/**
 * Utility class for creating status messages.
 */
object StatusUtility {
    /**
     * Creates a status message with the given code and message.
     *
     * @param code The status code to set.
     * @param message The status message to set.
     * @return A Status object containing the provided code and message.
     */
    fun createStatus(code: StatusCode, message: String): Status =
        Status.newBuilder().setCode(code).setMessage(message).build()
}
