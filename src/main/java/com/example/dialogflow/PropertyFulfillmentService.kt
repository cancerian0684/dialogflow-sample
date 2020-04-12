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
class PropertyFulfillmentService(private val jacksonFactory: JacksonFactory) {
    private val logger: Logger = LoggerFactory.getLogger(PropertyFulfillmentService::class.java)

    fun process(rawRequest: String?): String {
        val request: GoogleCloudDialogflowV2WebhookRequest = jacksonFactory
                .createJsonParser(rawRequest)
                .parse(GoogleCloudDialogflowV2WebhookRequest::class.java)
//        request.getOriginalDetectIntentRequest().getSource()

        var responseText = "There are 10 properties"
        request.queryResult.parameters.forEach { (t, u) ->
            when(t) {
                "modetype" -> {
                    val modeTypeList = request.queryResult.parameters[t] as ArrayList<String>
                    logger.info("Mode types = "+modeTypeList.joinToString(","))
                }
            }
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
}
