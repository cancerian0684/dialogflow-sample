package com.example.dialogflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;

@RestController
public class ActionController {

    private final Logger logger = LoggerFactory.getLogger(ActionController.class);

    @Autowired
    private ActionService actionService;

    @PostMapping(value = "/action")
    public Mono<String> process(@RequestBody Mono<String> rawRequest) {
        return rawRequest
                .doOnNext(s -> logger.info("Got the request from client"))
                .flatMap(s -> blockingGet(() -> actionService.process(s)));
    }

    // Run callable code on other thread pool than Netty event loop,
    // so blocking call will not block the event loop.
    private <T> Mono<T> blockingGet(final Callable<T> callable) {
        return Mono.fromCallable(callable).subscribeOn(Schedulers.elastic());
    }

}
