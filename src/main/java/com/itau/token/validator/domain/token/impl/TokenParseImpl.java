package com.itau.token.validator.domain.token.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.token.ITokenParse;
import com.itau.token.validator.domain.token.model.ConstantsMessageException;
import com.itau.token.validator.domain.token.model.TokenDTO;
import com.itau.token.validator.domain.token.model.exception.FalhaExtrairDadosToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenParseImpl implements ITokenParse {
    private static final Logger logger = LoggerFactory.getLogger(TokenParseImpl.class);
    private final MessageService messageService;
    private final ObjectMapper mapper;

    public TokenParseImpl(MessageService messageService, ObjectMapper mapper) {
        this.messageService = messageService;
        this.mapper = mapper;
    }


    @Override
    public JsonNode parseToken(TokenDTO tokenDTO) {
        String[] chunks = tokenDTO.tokenJwt().split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        try {
            return mapper.readTree(payload);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao extrair dados Token",e);
            throw new FalhaExtrairDadosToken(messageService.getMessage(ConstantsMessageException.ERRO_EXTRAIR_TOKEN.name()));
        }

    }
}
