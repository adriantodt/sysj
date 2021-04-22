package net.adriantodt.sysj.utils

import io.micronaut.scheduling.cron.CronExpression
import java.time.Duration
import java.time.ZonedDateTime

fun CronExpression.nextTime(): Duration {
    val now = ZonedDateTime.now()
    return Duration.ofMillis(nextTimeAfter(now).toInstant().toEpochMilli() - now.toInstant().toEpochMilli())
}
