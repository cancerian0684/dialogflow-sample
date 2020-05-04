package com.shunya.dialogflow

import com.google.api.client.json.JsonGenerator
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookRequest
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
        val richResponses = ArrayList<GoogleCloudDialogflowV2IntentMessage>()
        when (request.queryResult.intent.displayName) {
            "token-req" -> {
                val tokenType = request.queryResult.parameters["token_type"] as String?
                val project = request.queryResult.parameters["project"] as String?
                logger.info("Toke type request is $tokenType")
                responseText = when (project) {
                    "sunblinds" -> {
                        tokenService.sunblindsToken("cancerian0684@gmail.com", "123@cba", "acme", "acmesecret")
                    }
                    "cart67" -> {
                        tokenService.cart67Token("8010106513", "test1234", "acme", "acmesecret")
                    }
                    "espion" -> {
                        tokenService.mTestToken("cancerian0684@gmail.com", "123@cba", "acme", "acmesecret")
                    }
                    "shunya" -> {
                        tokenService.shunyaToken("cancerian0684@gmail.com", "1234", "acme", "acmesecret")
                    }
                    else -> {
                        tokenService.mTestToken("cancerian0684@gmail.com", "123@cba", "acme", "acmesecret")
                    }
                }
                richResponses += slackPayload("Hi, AccessToken for project *$project* is:")
                richResponses += slackPayload("```$responseText```")
            }
        }
        logger.info("request = $request")
        val response = GoogleCloudDialogflowV2WebhookResponse().apply {
            fulfillmentMessages = richResponses
        }
        val stringWriter = StringWriter()
        withContext(Dispatchers.IO) {
            val jsonGenerator: JsonGenerator = jacksonFactory.createJsonGenerator(stringWriter)
            jsonGenerator.serialize(response)
            jsonGenerator.flush()
        }
        return stringWriter.toString()
    }
    
    fun slackPayload(text: String): GoogleCloudDialogflowV2IntentMessage {
        val msg = GoogleCloudDialogflowV2IntentMessage()
        msg.platform = "SLACK"
        val map = HashMap<String, Any>().apply {
            put("text", text)
        }
        val payload = HashMap<String, Any>().apply {
            put("slack", map)
        }
        msg.payload = payload
        return msg
    }
}
