package com.alurkerja.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CommonRs {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public CommonRs() {
        super();
    }

    public CommonRs(int status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public CommonRs(int status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
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
