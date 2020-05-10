package com.shunya.dialogflow

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.apache.commons.codec.binary.Base64
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class TokenService(private val objectMapper: ObjectMapper) {
    private val webClient: WebClient = WebClient
            .builder()
            .baseUrl("https://dapi.shunyafoundation.com")
            .build()

    suspend fun sunblindsToken(username: String, password: String, clientId: String, clientSecret: String): String {
        val credentials = "acme:acmesecret"
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))

        val formData = LinkedMultiValueMap<String, String>()
        formData.add("grant_type", "password")
        formData.add("username", "cancerian0684@gmail.com")
        formData.add("password", "123@cba")
        formData.add("scope", "openid")

        val mono = webClient.post()
                .uri("/sunblinds-auth/oauth/token")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic $encodedCredentials")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .awaitBody<String>()
        val jsonNode = withContext(IO) {
             objectMapper.readTree(mono)
        }
        return jsonNode.get("access_token").asText()
    }
    
    suspend fun mTestToken(username: String, password: String, clientId: String, clientSecret: String): String {
        val credentials = "acme:acmesecret"
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))
        
        val formData = LinkedMultiValueMap<String, String>()
        formData.add("grant_type", "password")
        formData.add("username", "cancerian0684@gmail.com")
        formData.add("password", "1234")
        formData.add("scope", "openid")
        
        val mono = webClient.post()
                .uri("/uptime-sso/oauth/token")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic $encodedCredentials")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .awaitBody<String>()
        val jsonNode = withContext(IO) {
            objectMapper.readTree(mono)
        }
        return jsonNode.get("access_token").asText()
    }
    
    suspend fun cart67Token(username: String, password: String, clientId: String, clientSecret: String): String {
        val credentials = "acme:acmesecret"
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))
        
        val formData = LinkedMultiValueMap<String, String>()
        formData.add("grant_type", "password")
        formData.add("username", "8010106513")
        formData.add("password", "test1234")
        formData.add("scope", "openid")
        
        val mono = webClient.post()
                .uri("/cart67-auth/oauth/token")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic $encodedCredentials")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .awaitBody<String>()
        val jsonNode = withContext(IO) {
            objectMapper.readTree(mono)
        }
        return jsonNode.get("access_token").asText()
    }
    
    suspend fun shunyaToken(username: String, password: String, clientId: String, clientSecret: String): String {
        val credentials = "acme:acmesecret"
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))
        
        val formData = LinkedMultiValueMap<String, String>()
        formData.add("grant_type", "password")
        formData.add("username", username)
        formData.add("password", password)
        formData.add("scope", "openid")
        
        val mono = webClient.post()
                .uri("https://api.shunyafoundation.com/uaa/oauth/token")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic $encodedCredentials")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .awaitBody<String>()
        val jsonNode = withContext(IO) {
            objectMapper.readTree(mono)
        }
        return jsonNode.get("access_token").asText()
    }

}