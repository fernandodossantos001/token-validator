package com.itau.token.validator.domain.token.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.token.impl.TokenParseImpl;
import com.itau.token.validator.domain.token.model.TokenDTO;
import com.itau.token.validator.domain.global.service.exception.FalhaExtrairDadosToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenParseImplTest {

    @InjectMocks
    private TokenParseImpl tokenParse;

    @Mock
    private MessageService messageService;

    @Mock
    private ObjectMapper mapper;


    @Test
    void testParseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
        ReflectionTestUtils.setField(tokenParse,"mapper", new ObjectMapper());
        JsonNode jsonNode = tokenParse.parseTokenPayload(new TokenDTO(token));

        String role = jsonNode.get("Role").asText();
        String seed = jsonNode.get("Seed").asText();
        String name = jsonNode.get("Name").asText();

        Assertions.assertNotNull(jsonNode);

        Assertions.assertEquals("Admin",role);
        Assertions.assertEquals("7841",seed);
        Assertions.assertEquals("Toninho Araujo",name);
    }

    @Test
    void testParseTokenException() throws JsonProcessingException {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
        when(mapper.readTree(anyString())).thenThrow(new JsonProcessingException("Simulated JSON processing error") {});
        when(messageService.getMessage(anyString())).thenReturn("Erro ao extrair dados token jwt, verifique o token informado.");
        FalhaExtrairDadosToken falhaExtrairDadosToken = Assertions.assertThrows(FalhaExtrairDadosToken.class, () -> tokenParse.parseTokenPayload(new TokenDTO(token)));

        Assertions.assertNotNull(falhaExtrairDadosToken);
        Assertions.assertEquals("Erro ao extrair dados token jwt, verifique o token informado.",falhaExtrairDadosToken.getMessage());


    }
}
