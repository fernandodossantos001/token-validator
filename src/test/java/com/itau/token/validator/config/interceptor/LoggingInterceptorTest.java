package com.itau.token.validator.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoggingInterceptorTest {

    private final LoggingInterceptor interceptor = new LoggingInterceptor();


    @AfterEach
    void cleanup() {
        MDC.clear(); // limpa o MDC após cada teste para evitar interferência
    }

    @Test
    void testPreHandle_withTransactionIdHeader() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();

        String transactionId = "12345-transaction-id";

        when(request.getHeader("x-transaction-id")).thenReturn(transactionId);

        boolean result = interceptor.preHandle(request, response, handler);

        assertTrue(result);
        assertEquals(transactionId, MDC.get("x-transaction-id"));
    }

    @Test
    void testPreHandle_withoutTransactionIdHeader() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();

        when(request.getHeader("x-transaction-id")).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, handler);

        assertTrue(result);
        String mdCValue = MDC.get("x-transaction-id");

        assertNotNull(mdCValue);
        // Verifica se o valor é um UUID válido
        assertDoesNotThrow(() -> UUID.fromString(mdCValue));
    }
}
