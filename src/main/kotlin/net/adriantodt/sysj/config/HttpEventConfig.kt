package net.adriantodt.sysj.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HttpEventConfig(val events: List<HttpSchedulerEvent>)

@Serializable
enum class Event {
    @SerialName("start")
    START
}

@Serializable
sealed class HttpSchedulerEvent {
    abstract val url: String

    @Serializable
    @SerialName("cron")
    data class Cron(
        val cron: String,
        override val url: String
    ) : HttpSchedulerEvent()

    @Serializable
    @SerialName("rate")
    data class Rate(
        val rate: String,
        override val url: String
    ) : HttpSchedulerEvent()
}
