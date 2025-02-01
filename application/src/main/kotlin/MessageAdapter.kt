import io.github.positionpal.events.Event
import io.github.positionpal.events.EventType

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
    fun postEvent(type: EventType, event: Event): Unit

    /**
     * Closes the message adapter, releasing any resources held.
     */
    fun close(): Unit
}
