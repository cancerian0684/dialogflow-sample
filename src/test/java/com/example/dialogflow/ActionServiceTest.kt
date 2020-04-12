package com.example.dialogflow

import com.google.api.client.json.jackson2.JacksonFactory
import org.junit.jupiter.api.Test

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
        val actionService = ActionService(JacksonFactory.getDefaultInstance())
        val process = actionService.process(json)
        println("Process = $process")
    }

}