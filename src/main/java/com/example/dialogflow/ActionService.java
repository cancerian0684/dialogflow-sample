package com.example.dialogflow;

import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;

@Service
public class ActionService {

    private final Logger logger = LoggerFactory.getLogger(ActionService.class);

    @Autowired
    private JacksonFactory jacksonFactory;

   public String process(String rawRequest) throws IOException {
       GoogleCloudDialogflowV2WebhookRequest request = jacksonFactory.createJsonParser(rawRequest)
               .parse(GoogleCloudDialogflowV2WebhookRequest.class);

//        request.getOriginalDetectIntentRequest().getSource()
       String responseText = "";
       switch (request.getQueryResult().getIntent().getDisplayName()) {
           case "NeedQuote":
               responseText = getQuote((String) request.getQueryResult().getParameters().get("TypeOfQuote"));

       }
       logger.info("request = {}", request);
       GoogleCloudDialogflowV2WebhookResponse response = new GoogleCloudDialogflowV2WebhookResponse();
       response.setFulfillmentText(responseText);

       StringWriter stringWriter = new StringWriter();
       JsonGenerator jsonGenerator = jacksonFactory.createJsonGenerator(stringWriter);
       jsonGenerator.serialize(response);
       jsonGenerator.flush();
       return stringWriter.toString();
   }

    private String getQuote(String typeOfQuote) {
        String responseText;
        responseText = "This is a great quote from category: "+ typeOfQuote;
        return responseText;
    }
}
