package com.itau.token.validator.adapter.input;

import com.itau.token.validator.api.V1Api;
import com.itau.token.validator.model.TokenRequest;
import com.itau.token.validator.model.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenValidatorAdapterInput implements V1Api {

    @Override
    public ResponseEntity<TokenResponse> validaToken(TokenRequest tokenRequest) {
        return V1Api.super.validaToken(tokenRequest);
    }
}
