package com.example.dialogflow

import org.apache.commons.codec.binary.Base64
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

//import java.util.*

@RestController
class TokenController(val tokenService: TokenService) {

    @GetMapping("/test-token")
    fun getToken(): Mono<String> {
        return tokenService.createToken("cancerian0684@gmail.com", "123@cba", "acme", "acmesecret")
    }

}

@Service
class TokenService {
    private val webClient: WebClient = WebClient
            .builder()
            .baseUrl("https://dapi.shunyafoundation.com/sunblinds-auth/oauth/token")
            .build()

    fun createToken(username: String, password: String, clientId: String, clientSecret: String): Mono<String> {
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
                .bodyToMono(String::class.java)
        return mono
    }

}