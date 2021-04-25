package net.adriantodt.sysj.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.adriantodt.sysj.tasks.HttpTasks
import java.io.File
import java.util.concurrent.TimeUnit

@Serializable
data class HttpTasksConfig(val events: List<HttpTaskConfig>) {
    fun configure(httpTasks: HttpTasks) {
        for (event in events) when (event) {
            is HttpTaskConfig.Cron -> configureEvent(httpTasks, event)
            is HttpTaskConfig.Rate -> configureEvent(httpTasks, event)
        }
    }

    private fun configureEvent(httpTasks: HttpTasks, event: HttpTaskConfig.Cron) {
        httpTasks.createCronTask(event.url, event.cron)
    }

    private fun configureEvent(httpTasks: HttpTasks, event: HttpTaskConfig.Rate) {
        val parts = event.rate.split(' ', limit = 2)
        require(parts.size == 2)
        val rate = parts[0].toLongOrNull() ?: throw IllegalArgumentException()
        val unit = timeUnitsMap[parts[1].toLowerCase()] ?: throw IllegalArgumentException()
        httpTasks.createRateTask(event.url, rate, unit)
    }

    private val timeUnitsMap by lazy {
        TimeUnit.values()
            .flatMap {
                val name = it.name.toLowerCase()
                listOf(
                    name to it,
                    name.removeSuffix("s") to it
                )
            }
            .toMap()
    }

    companion object {
        fun load(): HttpTasksConfig {
            return Json.decodeFromString(File("events.json").readText())
        }
    }
}

