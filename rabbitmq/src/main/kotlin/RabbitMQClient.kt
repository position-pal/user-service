import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Channel
import io.github.positionpal.AvroSerializer
import io.github.positionpal.Message


class RabbitMQClient {
    private val factory = ConnectionFactory().apply {
        host = "localhost"
        port = 5672
        username = "guest"
        password = "guest"
    }
    private val connection = factory.newConnection()
    private val channel: Channel = connection.createChannel()

    init {
        channel.queueDeclare("group_updates", false, false, false, null)
    }

    fun postEvent(message: Message) {
        channel.basicPublish("", "group_updates", null, message.data)
    }

    fun close() {
        channel.close()
        connection.close()
    }
}