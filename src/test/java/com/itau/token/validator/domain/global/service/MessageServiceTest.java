package com.itau.token.validator.domain.global.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageSource messageSource;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(messageSource);
    }

    @Test
    void testGetMessageWithParams() {
        String key = "greeting";
        Object[] params = new Object[] { "John" };
        String expectedMessage = "Hello, John!";

        when(messageSource.getMessage(key, params, Locale.getDefault())).thenReturn(expectedMessage);

        String actualMessage = messageService.getMessage(key, params);

        assertEquals(expectedMessage, actualMessage);
        verify(messageSource, times(1)).getMessage(key, params, Locale.getDefault());
    }

    @Test
    void testGetMessageWithoutParams() {
        String key = "farewell";
        String expectedMessage = "Goodbye!";

        when(messageSource.getMessage(key, null, Locale.getDefault())).thenReturn(expectedMessage);

        String actualMessage = messageService.getMessage(key);

        assertEquals(expectedMessage, actualMessage);
        verify(messageSource, times(1)).getMessage(key, null, Locale.getDefault());
    }
}
