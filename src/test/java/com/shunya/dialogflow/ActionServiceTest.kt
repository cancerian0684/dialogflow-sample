package com.shunya.dialogflow

import com.google.api.client.json.jackson2.JacksonFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class ActionServiceTest {

    @Test
    fun processQuote() {
        val json = """{
                      "responseId": "1f96d7cc-b832-424d-afea-9bee9844c997-266f04e0",
                      "queryResult": {
                        "queryText": "i want a inspirational quote",
                        "parameters": {
                          "TypeOfQuote": "Inspiration"
                        },
                        "allRequiredParamsPresent": true,
                        "fulfillmentMessages": [
                          {
                            "text": {
                              "text": [
                                ""
                              ]
                            }
                          }
                        ],
                        "outputContexts": [
                          {
                            "name": "projects/customer-care-741b4/agent/sessions/4562d86a-001b-9403-3ae8-9b062bb5ff95/contexts/__system_counters__",
                            "parameters": {
                              "no-input": 0,
                              "no-match": 0,
                              "TypeOfQuote": "Inspiration",
                              "TypeOfQuote.original": "inspirational"
                            }
                          }
                        ],
                        "intent": {
                          "name": "projects/customer-care-741b4/agent/intents/20823d63-c139-46c2-9201-d2a69e9dae5c",
                          "displayName": "NeedQuote"
                        },
                        "intentDetectionConfidence": 0.74210924,
                        "languageCode": "en"
                      },
                      "originalDetectIntentRequest": {
                        "payload": {}
                      },
                      "session": "projects/customer-care-741b4/agent/sessions/4562d86a-001b-9403-3ae8-9b062bb5ff95"
                    }"""
        val actionService = ActionService(JacksonFactory.getDefaultInstance(), Mockito.mock(TokenService::class.java))
        val process = actionService.process(json)
        println("Process = $process")
    }

    @Test
    fun `test access token`() {
        @Language("JSON") val json = "{\n  \"responseId\": \"99674552-92e3-4a76-9577-28cbbda3fc97-a14fa99c\",\n  \"queryResult\": {\n    \"queryText\": \"need token\",\n    \"parameters\": {\n      \"user\": \"\",\n      \"token_type\": \"\",\n      \"project\": \"\",\n      \"role\": \"\"\n    },\n    \"allRequiredParamsPresent\": true,\n    \"fulfillmentMessages\": [\n      {\n        \"text\": {\n          \"text\": [\n            \"\"\n          ]\n        }\n      }\n    ],\n    \"outputContexts\": [\n      {\n        \"name\": \"projects/admin-terminal-wkmpmw/agent/sessions/295e944b-7a6a-8af5-9297-142e9e9364df/contexts/__system_counters__\",\n        \"parameters\": {\n          \"no-input\": 0,\n          \"no-match\": 0,\n          \"user\": \"\",\n          \"user.original\": \"\",\n          \"token_type\": \"\",\n          \"token_type.original\": \"\",\n          \"project\": \"\",\n          \"project.original\": \"\",\n          \"role\": \"\",\n          \"role.original\": \"\"\n        }\n      }\n    ],\n    \"intent\": {\n      \"name\": \"projects/admin-terminal-wkmpmw/agent/intents/58eedff9-afb1-4c24-8531-a6fe18769428\",\n      \"displayName\": \"token-req\"\n    },\n    \"intentDetectionConfidence\": 0.7907897,\n    \"languageCode\": \"en\"\n  },\n  \"originalDetectIntentRequest\": {\n    \"payload\": {}\n  },\n  \"session\": \"projects/admin-terminal-wkmpmw/agent/sessions/295e944b-7a6a-8af5-9297-142e9e9364df\"\n}"
        val actionService = ActionService(JacksonFactory.getDefaultInstance(), Mockito.mock(TokenService::class.java))
        GlobalScope.launch {
            val process = actionService.processToken(json)
            println("Process = $process")
        }
    }

}