package net.adriantodt.sysj.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.adriantodt.sysj.params.Params
import net.adriantodt.sysj.tasks.HttpTasks
import java.io.File
import java.util.concurrent.TimeUnit

@Serializable
data class ParamsConfig(val params: Map<String, String>) {
    fun configure(params: Params) {
        params.map.putAll(this.params)
    }

    companion object {
        fun load(): ParamsConfig {
            return Json.decodeFromString(File("params.json").readText())
        }
    }
}

