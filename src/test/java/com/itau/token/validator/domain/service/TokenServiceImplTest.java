package com.itau.token.validator.domain.service;

import com.itau.token.validator.domain.token.impl.TokenServiceImpl;
import com.itau.token.validator.domain.token.model.TokenDTO;
import com.itau.token.validator.domain.token.model.TokenValidateDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class TokenServiceImplTest {


    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    void testx(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
        TokenValidateDTO valida = tokenService.valida(new TokenDTO(token));
        Assertions.assertNotNull(valida);

    }
}
