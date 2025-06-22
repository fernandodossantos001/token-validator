package com.itau.token.validator.domain.token.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.global.exception.FalhaExtrairDadosToken;
import com.itau.token.validator.domain.token.impl.TokenParseImpl;
import com.itau.token.validator.domain.token.impl.TokenServiceImpl;
import com.itau.token.validator.domain.token.model.ConstantsMessageException;
import com.itau.token.validator.domain.token.model.TokenDTO;
import com.itau.token.validator.domain.token.model.TokenValidateDTO;
import com.itau.token.validator.domain.validador.service.IValidadorStrategy;
import com.itau.token.validator.domain.validador.service.impl.NameValidadorServiceImpl;
import com.itau.token.validator.domain.validador.service.impl.RoleValidadorServiceImpl;
import com.itau.token.validator.domain.validador.service.impl.SeedValidadorServiceImpl;
import com.itau.token.validator.domain.validador.service.impl.ValidadorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class TokenServiceImplTest {


    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private MessageService messageService;

    private List<IValidadorStrategy> strategies;

    @BeforeEach
    void setup(){
        strategies = new ArrayList<>();
        SeedValidadorServiceImpl seedValidadorService = new SeedValidadorServiceImpl(messageService);
        RoleValidadorServiceImpl roleValidadorService = new RoleValidadorServiceImpl(messageService);
        NameValidadorServiceImpl nameValidadorService = new NameValidadorServiceImpl(messageService);
        strategies.add(seedValidadorService);
        strategies.add(roleValidadorService);
        strategies.add(nameValidadorService);
    }


    @Test
    void testValidaSucesso(){
        //Caso 1:
        TokenParseImpl tokenParse = new TokenParseImpl(messageService, new ObjectMapper());
        ValidadorServiceImpl validadorService = new ValidadorServiceImpl(strategies, messageService);
        tokenService = new TokenServiceImpl(tokenParse,validadorService);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
        TokenValidateDTO valida = tokenService.valida(new TokenDTO(token));
        Assertions.assertNotNull(valida);
        Assertions.assertTrue(valida.valido());
    }


    @Test
    void testValidaErroTokenInvalido(){
        //Caso 2:

        String mensagemErro = "Erro ao extrair dados token jwt, verifique o token informado.";
        Mockito.when(messageService.getMessage(ConstantsMessageException.ERRO_EXTRAIR_TOKEN.name())).thenReturn(mensagemErro);

        TokenParseImpl tokenParse = new TokenParseImpl(messageService, new ObjectMapper());
        ValidadorServiceImpl validadorService = new ValidadorServiceImpl(strategies, messageService);
        tokenService = new TokenServiceImpl(tokenParse,validadorService);
        String token = "eyJhbGciOiJzI1NiJ9.dfsdfsfryJSr2xrIjoiQWRtaW4iLCJTZrkIjoiNzg0MSIsIk5hbrUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05fsdfsIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
        FalhaExtrairDadosToken falhaExtrairDadosToken = Assertions.assertThrows(FalhaExtrairDadosToken.class, () -> tokenService.valida(new TokenDTO(token)));

        Assertions.assertNotNull(falhaExtrairDadosToken);
        Assertions.assertEquals(mensagemErro,falhaExtrairDadosToken.getMessage());


    }


    @Test
    void testValidaErroNoClaimName(){
        //Caso 3:
        String mensagemErro = "A claim Name não pode ter carácter de números";
        Mockito.when(messageService.getMessage(ConstantsMessageException.REGRA_NEGOCIO_NAME_INVALIDO.name())).thenReturn(mensagemErro);
        TokenParseImpl tokenParse = new TokenParseImpl(messageService, new ObjectMapper());
        ValidadorServiceImpl validadorService = new ValidadorServiceImpl(strategies, messageService);
        tokenService = new TokenServiceImpl(tokenParse,validadorService);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiRXh0ZXJuYWwiLCJTZWVkIjoiODgwMzciLCJOYW1lIjoiTTRyaWEgT2xpdmlhIn0.6YD73XWZYQSSMDf6H0i3-kylz1-TY_Yt6h1cV2Ku-Qs";
        TokenValidateDTO valida = tokenService.valida(new TokenDTO(token));
        Assertions.assertNotNull(valida);
        Assertions.assertFalse(valida.valido());
    }

    @Test
    void testValidaErroClaimsDesconhecido(){
        //Caso 4:
        String mensagemErroClaimInvalido = "Deve conter apenas 3 claims (Name, Role e Seed)";
        Mockito.when(messageService.getMessage(ConstantsMessageException.QUANTIDADE_CLAIM_INVALIDA.name())).thenReturn(mensagemErroClaimInvalido);
        TokenParseImpl tokenParse = new TokenParseImpl(messageService, new ObjectMapper());
        ValidadorServiceImpl validadorService = new ValidadorServiceImpl(strategies, messageService);
        tokenService = new TokenServiceImpl(tokenParse,validadorService);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiTWVtYmVyIiwiT3JnIjoiQlIiLCJTZWVkIjoiMTQ2MjciLCJOYW1lIjoiVmFsZGlyIEFyYW5oYSJ9.cmrXV_Flm5mfdpfNUVopY_I2zeJUy4EZ4i3Fea98zvY";
        TokenValidateDTO valida = tokenService.valida(new TokenDTO(token));
        Assertions.assertNotNull(valida);
        Assertions.assertFalse(valida.valido());
    }
}
