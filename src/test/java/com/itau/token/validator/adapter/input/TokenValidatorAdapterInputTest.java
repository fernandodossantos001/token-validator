package com.itau.token.validator.adapter.input;


import com.itau.token.validator.domain.token.ITokenService;
import com.itau.token.validator.domain.token.model.TokenDTO;
import com.itau.token.validator.domain.token.model.TokenValidateDTO;
import com.itau.token.validator.model.TokenRequest;
import com.itau.token.validator.model.TokenResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TokenValidatorAdapterInputTest {
    private ITokenService iTokenService;
    private TokenValidatorAdapterInput adapter;

    @BeforeEach
    void setUp() {
        iTokenService = mock(ITokenService.class);
        adapter = new TokenValidatorAdapterInput(iTokenService);
    }

    @Test
    void testValidaToken_comTokenValido() {
        // Arrange
        String tokenJwt = "abc.def.ghi";
        String xTransactionId = "trans-123";

        TokenRequest request = mock(TokenRequest.class);
        when(request.getTokenJwt()).thenReturn(tokenJwt);

        TokenValidateDTO validateDTO = mock(TokenValidateDTO.class);
        when(validateDTO.valido()).thenReturn(true);

        when(iTokenService.valida(any(TokenDTO.class))).thenReturn(validateDTO);

        // Act
        ResponseEntity<TokenResponse> response = adapter.validaToken(request, xTransactionId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getValido());
    }

    @Test
    void testValidaToken_comTokenInvalido() {
        // Arrange
        String tokenJwt = "invalid.jwt.token";
        String xTransactionId = "trans-999";

        TokenRequest request = mock(TokenRequest.class);
        when(request.getTokenJwt()).thenReturn(tokenJwt);

        TokenValidateDTO validateDTO = mock(TokenValidateDTO.class);
        when(validateDTO.valido()).thenReturn(false);

        when(iTokenService.valida(any(TokenDTO.class))).thenReturn(validateDTO);

        // Act
        ResponseEntity<TokenResponse> response = adapter.validaToken(request, xTransactionId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getValido());
    }
}
