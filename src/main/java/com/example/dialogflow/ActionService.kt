package com.example.dialogflow

import com.google.api.client.json.JsonGenerator
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookRequest
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.StringWriter

@Service
class ActionService(private val jacksonFactory: JacksonFactory,
                    val tokenService: TokenService) {
    private val logger: Logger = LoggerFactory.getLogger(ActionService::class.java)

    fun process(rawRequest: String?): String {
        val request: GoogleCloudDialogflowV2WebhookRequest = jacksonFactory
                .createJsonParser(rawRequest)
                .parse(GoogleCloudDialogflowV2WebhookRequest::class.java)
//        request.getOriginalDetectIntentRequest().getSource()

        var responseText = ""
        when (request.queryResult.intent.displayName) {
            "NeedQuote" -> responseText = getQuote(request.queryResult.parameters["TypeOfQuote"] as String?)
        }
        logger.info("request = {}", request)
        val response = GoogleCloudDialogflowV2WebhookResponse()
        response.fulfillmentText = responseText
        val stringWriter = StringWriter()
        val jsonGenerator: JsonGenerator = jacksonFactory.createJsonGenerator(stringWriter)
        jsonGenerator.serialize(response)
        jsonGenerator.flush()
        return stringWriter.toString()
    }

    private fun getQuote(typeOfQuote: String?): String {
        val responseText = "This is a great quote from category: $typeOfQuote"
        return responseText
    }

    suspend fun processToken(rawRequest: String?): String {
        logger.info("Incoming webhook request is")
        logger.info(rawRequest)
        val request: GoogleCloudDialogflowV2WebhookRequest = jacksonFactory
                .createJsonParser(rawRequest)
                .parse(GoogleCloudDialogflowV2WebhookRequest::class.java)
//        request.getOriginalDetectIntentRequest().getSource()

        var responseText: String? = ""
        when (request.queryResult.intent.displayName) {
            "token-req" -> {
               val tokenType =  request.queryResult.parameters["token_type"] as String?
                logger.info("Toke type request is $tokenType")
                responseText = tokenService.sunblindsToken("cancerian0684@gmail.com", "123@cba", "acme", "acmesecret")
            }
        }
        logger.info("request = $request")
        val response = GoogleCloudDialogflowV2WebhookResponse()
        response.fulfillmentText = responseText
        val stringWriter = StringWriter()
        val jsonGenerator: JsonGenerator = jacksonFactory.createJsonGenerator(stringWriter)
        jsonGenerator.serialize(response)
        jsonGenerator.flush()
        return stringWriter.toString()
    }
}
