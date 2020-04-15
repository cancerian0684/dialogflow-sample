package com.example.dialogflow

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.concurrent.Callable

@RestController
class ActionController(val actionService: ActionService) {
    private val logger: Logger = LoggerFactory.getLogger(ActionController::class.java)

    @PostMapping(value = ["/action"])
    fun process(@RequestBody rawRequest: Mono<String>): Mono<String> {
        return rawRequest
                .doOnNext { s: String -> logger.info("Got the request from client $s") }
                .flatMap { s: String -> blockingGet(Callable { actionService.process(s) }) }
    }

    @PostMapping(value = ["/token"])
    fun token(@RequestBody rawRequest: Mono<String>): Mono<String> {
        return rawRequest
                .doOnNext { s: String -> logger.info("Got the request from client $s") }
                .flatMap { s: String -> blockingGet(Callable { actionService.processToken(s) }) }
    }

    // Run callable code on other thread pool than Netty event loop,
    // so blocking call will not block the event loop.
    private fun <T> blockingGet(callable: Callable<T>): Mono<T> {
        return Mono.fromCallable(callable).subscribeOn(Schedulers.elastic())
    }
}
