package com.yingxs.security.support;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleResponse {

    public SimpleResponse(String message) {
        this.message = message;
    }
    public SimpleResponse(String message,Object data) {
        this.message = message;
        this.data = data;
    }

    private String message;
    private Object data;

    public Object getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
