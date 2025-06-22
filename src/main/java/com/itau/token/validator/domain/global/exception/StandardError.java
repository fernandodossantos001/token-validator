package com.itau.token.validator.domain.global.exception;

import java.io.Serializable;
import java.util.Calendar;

public class StandardError implements Serializable {

    private Integer status;
    private String mensagem;
    private Calendar timeStamp;

    public StandardError(Integer status, String mensagem, Calendar timeStamp) {
        this.status = status;
        this.mensagem = mensagem;
        this.timeStamp = timeStamp;
    }
    public Integer getStatus() {
        return status;
    }
    public String getMensagem() {
        return mensagem;
    }
    public Calendar getTimeStamp() {
        return timeStamp;
    }
}
