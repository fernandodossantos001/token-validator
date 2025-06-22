package com.itau.token.validator.adapter.handler;

import com.itau.token.validator.domain.global.exception.BaseUncheckedException;
import com.itau.token.validator.domain.global.exception.FieldMessage;
import com.itau.token.validator.domain.global.exception.StandardError;
import com.itau.token.validator.domain.global.exception.ValidationError;
import com.itau.token.validator.domain.validador.exception.ValidadorException;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResourceExceptionHandlerTest {

    private ResourceExceptionHandler handler;
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        handler = new ResourceExceptionHandler();
        mockRequest = mock(HttpServletRequest.class);
    }

    @Test
    void testValidadorException() {

        Map<String, ValidadorDTO> itensValidacao = new HashMap<>();
        itensValidacao.put("token",new ValidadorDTO(Optional.of("Token inválido"),true));
        itensValidacao.put("signature",new ValidadorDTO(Optional.of("Assinatura inválida"),true));

        ValidadorException validadorException = new ValidadorException("Erro ao validar token",itensValidacao);

        ResponseEntity<StandardError> response = handler.validadorException(validadorException);

        assertEquals(400, response.getStatusCodeValue());
        assertInstanceOf(ValidationError.class, response.getBody());

        ValidationError body = (ValidationError) response.getBody();
        assertEquals(2, body.getErrors().size());
        assertEquals(400, body.getStatus());
        assertEquals("Erro de validação do token JWT !", body.getMensagem());
        Assertions.assertNotNull(body.getTimeStamp());

        FieldMessage fieldMessage = body.getErrors().get(0);

        Assertions.assertNotNull(fieldMessage.getMessage());
        Assertions.assertNotNull(fieldMessage.getFieldName());

    }

    @Test
    void testBusinessValidation() {
        BaseUncheckedException ex = new BaseUncheckedException("Erro de negócio");

        ResponseEntity<StandardError> response = handler.businessValidation(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Erro de negócio", response.getBody().getMensagem());
    }

    @Test
    void testGenericException() {
        Exception ex = new Exception("Erro genérico");

        ResponseEntity<StandardError> response = handler.exception(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Erro genérico", response.getBody().getMensagem());
    }
}
