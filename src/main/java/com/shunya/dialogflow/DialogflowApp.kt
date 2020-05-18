package com.shunya.dialogflow

import com.google.api.client.json.jackson2.JacksonFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean

@EnableDiscoveryClient
@SpringBootApplication
class DialogflowApp {

    @Bean
    fun jacksonFactory(): JacksonFactory {
        return JacksonFactory.getDefaultInstance()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(DialogflowApp::class.java, *args)
        }
    }
}

inline fun measureTimeMillis(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}