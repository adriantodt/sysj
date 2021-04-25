package net.adriantodt.sysj.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class HttpTaskConfig {
    abstract val url: String

    @Serializable
    @SerialName("cron")
    data class Cron(
        val cron: String,
        override val url: String
    ) : HttpTaskConfig()

    @Serializable
    @SerialName("rate")
    data class Rate(
        val rate: String,
        override val url: String
    ) : HttpTaskConfig()
}
