# dialogflow-sample
Sample Dialog flow fulfilment API with Spring Boot 2

## Sample Fulfillment API call

POST http://localhost:8080/action

Response Body

```json
{
  "responseId": "fdf64715-9fc5-4975-95bc-30c46a3b595c",
  "queryResult": {
    "queryText": "give me a happiness quote",
    "parameters": {
      "TypeOfQuote": "Happiness"
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
    "intent": {
      "name": "projects/customer-care-741b4/agent/intents/20823d63-c139-46c2-9201-d2a69e9dae5c",
      "displayName": "NeedQuote"
    },
    "intentDetectionConfidence": 1,
    "languageCode": "en"
  },
  "originalDetectIntentRequest": {
    "payload": {}
  },
  "session": "projects/customer-care-741b4/agent/sessions/99a238da-13dc-db87-6480-eed1201b4846"
}
```


Response
```json
{"fulfillmentText":"This is a great quote from category: Happiness"}
```
