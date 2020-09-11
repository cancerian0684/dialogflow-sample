package com.shunya.dialogflow

import org.slf4j.Logger
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.concurrent.Callable

@RestController
class ActionController(val actionService: ActionService) {
    private val logger: Logger = loggerFor<ActionController>()
    
    @PostMapping(value = ["/action"])
    fun process(@RequestBody rawRequest: Mono<String>): Mono<String> {
        return rawRequest
                .doOnNext { s: String -> logger.info("Got the request from client $s") }
                .flatMap { s: String -> blockingGet { actionService.process(s) } }
    }
    
    @PostMapping(value = ["/token"], produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun token(@RequestBody rawRequest: String): String {
        return actionService.processToken(rawRequest)
    }
    
    @PostMapping("/raw-json")
    fun acceptRaw(@RequestBody rawJson: String) {
        logger.info("Raw Json: $rawJson")
    }
    
    @PostMapping(value = ["/raw-json-2"], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun greetingJson(httpEntity: HttpEntity<String>): String {
        val json: String? = httpEntity.body
        println("json = $json")
        // json contains the plain json string
        return "ok"
    }
    
    // Run callable code on other thread pool than Netty event loop,
    // so blocking call will not block the event loop.
    private fun <T> blockingGet(callable: Callable<T>): Mono<T> {
        return Mono.fromCallable(callable).subscribeOn(Schedulers.elastic())
    }
}
