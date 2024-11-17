import io.github.positionpal.Event
import io.github.positionpal.MessageType

/**
 * Interface representing a message adapter for posting events and closing the connection.
 */
interface MessageAdapter {

    /**
     * Posts an event to the message adapter.
     *
     * @param type The type of the message.
     * @param event The event to be posted.
     */
    fun postEvent(type: MessageType, event: Event): Unit

    /**
     * Closes the message adapter, releasing any resources held.
     */
    fun close(): Unit
}
