package com.smartbear.assignment.model;

import org.springframework.stereotype.Component;

@Component
public class ExceptionResponse {
    private int code;
    private String descrition;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }
}
