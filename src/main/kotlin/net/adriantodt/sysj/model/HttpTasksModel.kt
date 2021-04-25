package net.adriantodt.sysj.model

import net.adriantodt.sysj.tasks.CronTask
import net.adriantodt.sysj.tasks.RateTask
import java.util.concurrent.TimeUnit

sealed class HttpTaskModel {
    abstract val url: String
}

data class CronTaskModel(override val url: String, val cron: String) : HttpTaskModel() {
    constructor(task: CronTask) : this(task.url, task.cron)
}

class RateTaskModel(override val url: String, val rate: Long, val unit: TimeUnit) : HttpTaskModel() {
    constructor(task: RateTask) : this(task.url, task.rate, task.unit)
}
