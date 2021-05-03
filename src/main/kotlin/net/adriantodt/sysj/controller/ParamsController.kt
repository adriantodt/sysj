package net.adriantodt.sysj.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import net.adriantodt.sysj.params.Params

@Controller("/params")
class ParamsController(private val params: Params) {
    @Get
    fun entries(): HttpResponse<List<Map<String, String>>> {
        return HttpResponse.ok(params.map.entries.map { (k, v) -> mapOf("key" to k, "value" to v) })
    }

    @Get("keys")
    fun keys(): HttpResponse<Set<String>> {
        return HttpResponse.ok(params.map.keys)
    }

    @Get("count")
    fun count(): HttpResponse<Int> {
        return HttpResponse.ok(params.map.size)
    }

    @Post
    fun putValues(@Body map: Map<String, String?>) {
        for ((key, value) in map) {
            if (value != null) {
                params.map["/$key"] = value
            } else {
                params.map.remove("/$key")
            }
        }
    }

    @Get("value{/k:.*}")
    fun value(k: String?): HttpResponse<Map<String, String>> {
        val key = "/${k ?: ""}"
        val value = params.map[key] ?: return HttpResponse.notFound(mapOf("key" to key))
        return HttpResponse.ok(mapOf("key" to key, "value" to value))
    }

    @Get("remove{/k:.*}")
    fun removeValue(k: String?): HttpResponse<Boolean> {
        val removed = params.map.remove("/${k ?: ""}") != null
        return if (removed) HttpResponse.ok(true) else HttpResponse.notFound(false)
    }
}
