package com.itau.token.validator.adapter.input;

import com.itau.token.validator.api.V1Api;
import com.itau.token.validator.domain.token.ITokenService;
import com.itau.token.validator.domain.token.model.TokenDTO;
import com.itau.token.validator.domain.token.model.TokenValidateDTO;
import com.itau.token.validator.model.TokenRequest;
import com.itau.token.validator.model.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenValidatorAdapterInput implements V1Api {

    private static final Logger logger = LoggerFactory.getLogger(TokenValidatorAdapterInput.class);

    private final ITokenService iTokenService;

    public TokenValidatorAdapterInput(ITokenService iTokenService) {
        this.iTokenService = iTokenService;
    }

    @Override
    public ResponseEntity<TokenResponse> validaToken(TokenRequest tokenRequest,String xTransactionId) {

        logger.info("Token Recebido - {}",tokenRequest.getTokenJwt());
        TokenDTO tokenDTO = new TokenDTO(tokenRequest.getTokenJwt());
        TokenValidateDTO isValideToken = iTokenService.valida(tokenDTO);
        TokenResponse tokenResponse = new TokenResponse().valido(isValideToken.valido());
        return ResponseEntity.ok(tokenResponse);
    }
}
