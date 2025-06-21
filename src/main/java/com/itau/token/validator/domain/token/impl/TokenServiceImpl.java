package com.itau.token.validator.domain.token.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.itau.token.validator.domain.token.ITokenParse;
import com.itau.token.validator.domain.token.ITokenService;
import com.itau.token.validator.domain.token.model.TokenDTO;
import com.itau.token.validator.domain.token.model.TokenValidateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TokenServiceImpl implements ITokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    private final ITokenParse iTokenParse;

    public TokenServiceImpl(ITokenParse iTokenParse) {
        this.iTokenParse = iTokenParse;
    }

    @Override
    public TokenValidateDTO valida(TokenDTO tokenDTO) {
        JsonNode jsonNode = iTokenParse.parseToken(tokenDTO);

        return null;
    }
}
