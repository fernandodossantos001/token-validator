package com.itau.token.validator.domain.global.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class    MessageService {
    private MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, Object... params) {
        return messageSource.getMessage(key, params, Locale.getDefault());
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }
}
