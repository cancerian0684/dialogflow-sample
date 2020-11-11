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
import org.springframework.web.reactive.function.client.awaitEntity
import org.springframework.web.reactive.function.client.awaitExchange

@Service
class TokenService(private val objectMapper: ObjectMapper) {
    private val webClient: WebClient = WebClient
            .builder()
            .build()

    suspend fun accessToken(username: String, password: String, clientId: String, clientSecret: String, url: String): String {
        val encodedCredentials = String(Base64.encodeBase64("$clientId:$clientSecret".toByteArray()))

        val formData = LinkedMultiValueMap<String, String>()
        formData.add("grant_type", "password")
        formData.add("username", username)
        formData.add("password", password)
        formData.add("scope", "openid")

        val mono = webClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic $encodedCredentials")
                .body(BodyInserters.fromFormData(formData))
                .awaitExchange()
                .awaitEntity<String>()
        return if(mono.statusCode.is2xxSuccessful) {
            val jsonNode = withContext(IO) {
                objectMapper.readTree(mono.body)
            }
            jsonNode.get("access_token").asText()
        } else {
            mono.statusCode.reasonPhrase
        }
    }
    
    @Deprecated("duplicate")
    suspend fun mTestToken(username: String, password: String, clientId: String, clientSecret: String): String {
        val credentials = "acme:acmesecret"
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))
        
        val formData = LinkedMultiValueMap<String, String>()
        formData.add("grant_type", "password")
        formData.add("username", username)
        formData.add("password", password)
        formData.add("scope", "openid")
        
        val mono = webClient.post()
                .uri("https://dapi.shunyafoundation.com/espion-auth/oauth/token")
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
    
    @Deprecated("duplicate")
    suspend fun cart67Token(username: String, password: String, clientId: String, clientSecret: String): String {
        val credentials = "acme:acmesecret"
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))
        
        val formData = LinkedMultiValueMap<String, String>()
        formData.add("grant_type", "password")
        formData.add("username", username)
        formData.add("password", password)
        formData.add("scope", "openid")
        
        val mono = webClient.post()
                .uri("https://dapi.shunyafoundation.com/cart67-auth/oauth/token")
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
    
    @Deprecated("duplicate")
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
