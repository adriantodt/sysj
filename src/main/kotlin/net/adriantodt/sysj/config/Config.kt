package net.adriantodt.sysj.config

import io.micronaut.scheduling.cron.CronExpression
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.adriantodt.sysj.tasks.HttpTasks
import java.io.File
import java.util.concurrent.TimeUnit

object Config {

    fun configurateHttpTasks(httpTasks: HttpTasks) {
        val config = Json.decodeFromString<HttpEventConfig>(File("events.json").readText())

        for (event in config.events) when (event) {
            is HttpSchedulerEvent.Cron -> configurateEvent(httpTasks, event)
            is HttpSchedulerEvent.Rate -> configurateEvent(httpTasks, event)
        }
    }

    private fun configurateEvent(httpTasks: HttpTasks, event: HttpSchedulerEvent.Cron) {
        val cronExpr = CronExpression.create(event.cron)
        httpTasks.createCronTask(event.url, cronExpr)
    }

    private fun configurateEvent(httpTasks: HttpTasks, event: HttpSchedulerEvent.Rate) {
        val parts = event.rate.split(' ', limit = 2)
        require(parts.size == 2)
        val rate = parts[0].toLongOrNull() ?: throw IllegalArgumentException()
        val unit = timeUnitsMap[parts[1].toLowerCase()] ?: throw IllegalArgumentException()
        httpTasks.createRateTask(event.url, rate, unit)
    }

    private val timeUnitsMap by lazy {
        val values = TimeUnit.values()
        values.flatMap {
            val name = it.name.toLowerCase()
            listOf(name to it, name.removeSuffix("s") to it)
        }.toMap()
    }
}
