package com.shunya.dialogflow

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController(val tokenService: TokenService) {

    @GetMapping(value = ["/test-token"], produces = ["application/json"])
    suspend fun getToken(): String {
        return tokenService.token("cancerian0684@gmail.com", "123@cba", "acme", "acmesecret", "https://dapi.shunyafoundation.com/sunblinds-auth/oauth/token")
    }

}