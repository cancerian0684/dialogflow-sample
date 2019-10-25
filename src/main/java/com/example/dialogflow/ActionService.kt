package com.example.dialogflow

import com.google.api.client.json.JsonGenerator
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookRequest
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.StringWriter

@Service
class ActionService {
    private val logger: Logger = LoggerFactory.getLogger(ActionService::class.java)

    @Autowired
    private lateinit var jacksonFactory: JacksonFactory

    @Throws(IOException::class)
    fun process(rawRequest: String?): String {
        val request: GoogleCloudDialogflowV2WebhookRequest = jacksonFactory
                .createJsonParser(rawRequest)
                .parse<GoogleCloudDialogflowV2WebhookRequest>(GoogleCloudDialogflowV2WebhookRequest::class.java)

//        request.getOriginalDetectIntentRequest().getSource()
//

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
}
