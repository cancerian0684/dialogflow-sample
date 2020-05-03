package com.shunya.dialogflow

import com.google.api.client.json.jackson2.JacksonFactory
import org.junit.jupiter.api.Test

class PropertyFulfillmentServiceTest {

    @Test
    fun processPropertyRequest() {
        val json = "{\n  \"responseId\": \"6cb7b300-3570-46ae-b5f7-ea70381f0ae0-266f04e0\",\n  \"queryResult\": {\n    \"queryText\": \"sale or lease\",\n    \"action\": \"CheckPropertyLocation.CheckPropertyLocation-custom\",\n    \"parameters\": {\n      \"modetype\": [\n        \"sale\",\n        \"lease\"\n      ]\n    },\n    \"allRequiredParamsPresent\": true,\n    \"fulfillmentText\": \"Let me check what we have for you\",\n    \"fulfillmentMessages\": [\n      {\n        \"text\": {\n          \"text\": [\n            \"Let me check what we have for you\"\n          ]\n        }\n      }\n    ],\n    \"outputContexts\": [\n      {\n        \"name\": \"projects/agentk2-xxrtgn/agent/sessions/2d8b60de-9eab-24e4-a7bb-9829170c6108/contexts/modetype\",\n        \"lifespanCount\": 5,\n        \"parameters\": {\n          \"Property\": [\n            \"condo\",\n            \"Detached\"\n          ],\n          \"modetype\": [\n            \"sale\",\n            \"lease\"\n          ],\n          \"Place\": [\n            \"mexico\"\n          ],\n          \"Place.original\": [\n            \"mexico\"\n          ],\n          \"modetype.original\": [\n            \"sale\",\n            \"lease\"\n          ],\n          \"Property.original\": [\n            \"condo\",\n            \"detached\"\n          ]\n        }\n      },\n      {\n        \"name\": \"projects/agentk2-xxrtgn/agent/sessions/2d8b60de-9eab-24e4-a7bb-9829170c6108/contexts/checkpropertylocation-followup\",\n        \"lifespanCount\": 1,\n        \"parameters\": {\n          \"modetype.original\": [\n            \"sale\",\n            \"lease\"\n          ],\n          \"Property.original\": [\n            \"condo\",\n            \"detached\"\n          ],\n          \"Property\": [\n            \"condo\",\n            \"Detached\"\n          ],\n          \"Place\": [\n            \"mexico\"\n          ],\n          \"modetype\": [\n            \"sale\",\n            \"lease\"\n          ],\n          \"Place.original\": [\n            \"mexico\"\n          ]\n        }\n      }\n    ],\n    \"intent\": {\n      \"name\": \"projects/agentk2-xxrtgn/agent/intents/c3971445-4e45-46a2-967c-a31dc20289ff\",\n      \"displayName\": \"CheckPropertyLocationMode-followup\"\n    },\n    \"intentDetectionConfidence\": 0.8000621,\n    \"languageCode\": \"en\"\n  }\n}"
        val fulfillmentService = PropertyFulfillmentService(JacksonFactory.getDefaultInstance())
        val process = fulfillmentService.process(json)
        println("Process = $process")
    }
}