package com.example.dialogflow

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.codec.binary.Base64
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

//import java.util.*

@RestController
class TokenController(val tokenService: TokenService) {

    @GetMapping(value = ["/test-token"], produces = ["application/json"])
    suspend fun getToken(): String {
        return tokenService.createToken("cancerian0684@gmail.com", "123@cba", "acme", "acmesecret")
    }

}

@Service
class TokenService(private val objectMapper: ObjectMapper) {
    private val webClient: WebClient = WebClient
            .builder()
            .baseUrl("https://dapi.shunyafoundation.com/sunblinds-auth/oauth/token")
            .build()

    suspend fun createToken(username: String, password: String, clientId: String, clientSecret: String): String {
        val credentials = "acme:acmesecret"
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))

        val formData = LinkedMultiValueMap<String, String>()
        formData.add("grant_type", "password")
        formData.add("username", "cancerian0684@gmail.com")
        formData.add("password", "123@cba")
        formData.add("scope", "openid")

        val mono = webClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic $encodedCredentials")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .awaitBody<String>()
        val jsonNode = objectMapper.readTree(mono)
        return jsonNode.get("access_token").asText()
    }

}