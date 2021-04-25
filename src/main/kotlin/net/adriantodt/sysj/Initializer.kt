package net.adriantodt.sysj

import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceReadyEvent
import io.micronaut.scheduling.annotation.Async
import mu.KLogging
import net.adriantodt.sysj.config.HttpTasksConfig
import net.adriantodt.sysj.config.ParamsConfig
import net.adriantodt.sysj.params.Params
import net.adriantodt.sysj.tasks.HttpTasks
import javax.inject.Singleton

@Singleton
open class Initializer(
    private val httpTasks: HttpTasks,
    private val params: Params
) : ApplicationEventListener<ServiceReadyEvent> {
    companion object : KLogging()

    @Async
    override fun onApplicationEvent(event: ServiceReadyEvent) {
        logger.info("Configuring HttpTasks...")
        HttpTasksConfig.load().configure(httpTasks)
        logger.info("Configuring Params...")
        ParamsConfig.load().configure(params)
    }
}
