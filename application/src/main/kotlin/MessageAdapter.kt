import io.github.positionpal.Message

interface MessageAdapter {
    fun postEvent(message: Message): Unit
    fun close(): Unit
}