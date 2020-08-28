package com.shunya.dialogflow

import com.google.api.client.json.JsonGenerator
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageText
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
                logger.info("Token type request is $tokenType")
                responseText = when (project) {
                    "sunblinds" -> {
                        tokenService.token("cancerian0684@gmail.com", "password", "acme", "acmesecret", "https://dapi.shunyafoundation.com/sunblinds-auth/oauth/token")
                    }
                    "cart67" -> {
                        tokenService.token("8010106513", "test1234", "acme", "acmesecret", "https://dapi.shunyafoundation.com/cart67-auth/oauth/token")
                    }
                    "espion" -> {
                        tokenService.token("cancerian0684@gmail.com", "12345", "acme", "acmesecret", "https://dapi.shunyafoundation.com/espion-auth/oauth/token")
                    }
                    "shunya" -> {
                        tokenService.token("cancerian0684@gmail.com", "1234", "acme", "acmesecret", "https://api.shunyafoundation.com/uaa/oauth/token")
                    }
                    else -> {
                        tokenService.token("cancerian0684@gmail.com", "123@cba", "acme", "acmesecret", "https://dapi.shunyafoundation.com/espion-auth/oauth/token")
                    }
                }
                richResponses += slackPayload("Hi, AccessToken for project *$project* is:")
                richResponses += slackPayload("```$responseText```")
    
                richResponses += webDemoPayload("Here is the $project AccessToken:")
                richResponses += webDemoPayload(responseText)
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
    
    fun webDemoPayload(text: String?): GoogleCloudDialogflowV2IntentMessage {
        val msg = GoogleCloudDialogflowV2IntentMessage()
        msg.text = GoogleCloudDialogflowV2IntentMessageText().setText(listOf(text))
        return msg
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
