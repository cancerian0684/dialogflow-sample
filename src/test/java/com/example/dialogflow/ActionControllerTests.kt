package com.example.dialogflow

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(ActionController::class)
class ActionControllerTests {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var actionService: ActionService

    @Test
    fun testPostDialogflowTest1() {
//		Employee employee = Employee.builder().id(1).city("delhi").age(23).name("ABC").build();
        val jsonRequest = """{
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
//		when(employeeService.getEmployeeById(1)).thenReturn(employeeMono);
        webTestClient.post()
                .uri("/action")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(jsonRequest), String::class.java)
                .exchange()
                .expectStatus().isOk
//                .expectBody(String::class.java)
//                .value { t ->  }
    }
}