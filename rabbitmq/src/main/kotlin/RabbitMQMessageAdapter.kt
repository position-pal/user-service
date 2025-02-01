import com.rabbitmq.client.AMQP
import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import io.github.positionpal.EventSerializer
import io.github.positionpal.events.AddedMemberToGroup
import io.github.positionpal.events.Event
import io.github.positionpal.events.EventType
import io.github.positionpal.events.GroupCreated
import io.github.positionpal.events.GroupDeleted
import io.github.positionpal.events.RemovedMemberToGroup

/**
 * A message adapter for RabbitMQ to handle posting events and managing connections.
 *
 * @property rabbitMQHost The RabbitMQ host address.
 * @property rabbitMQPort The RabbitMQ port number.
 * @property rabbitMQUsername The RabbitMQ username.
 * @property rabbitMQPassword The RabbitMQ password.
 * @property serializer The serializer for events.
 */
class RabbitMQMessageAdapter(
    val rabbitMQHost: String = "localhost",
    val rabbitMQPort: Int = 5672,
    val rabbitMQUsername: String = "guest",
    val rabbitMQPassword: String = "guest",
    private val serializer: EventSerializer,
) : MessageAdapter {

    private val factory = ConnectionFactory().apply {
        host = rabbitMQHost
        port = rabbitMQPort
        username = rabbitMQUsername
        password = rabbitMQPassword
    }
    private val connection = factory.newConnection()
    private val channel: Channel = connection.createChannel()

    init {
        channel.exchangeDeclare("group_updates_exchange", BuiltinExchangeType.HEADERS, true)
    }

    /**
     * Posts an event to the RabbitMQ exchange.
     *
     * @param type The type of the message.
     * @param event The event to be posted.
     * @throws IllegalArgumentException if the event type is not supported.
     */
    override fun postEvent(type: EventType, event: Event) {
        val headers = mapOf(
            "message_type" to type.toString(),
        )

        val properties = AMQP.BasicProperties.Builder()
            .headers(headers)
            .build()

        val byte = when (type) {
            EventType.GROUP_CREATED ->
                serializer.serializeGroupCreated(event as GroupCreated?)
            EventType.GROUP_DELETED ->
                serializer.serializeGroupDeleted(event as GroupDeleted?)
            EventType.MEMBER_ADDED ->
                serializer.serializeAddedMemberToGroup(event as AddedMemberToGroup?)
            EventType.MEMBER_REMOVED ->
                serializer.serializeRemovedMemberToGroup(event as RemovedMemberToGroup?)
        }

        channel.basicPublish("group_updates_exchange", "", properties, byte)
    }

    /**
     * Closes the RabbitMQ channel and connection.
     */
    override fun close() {
        channel.close()
        connection.close()
    }
}
