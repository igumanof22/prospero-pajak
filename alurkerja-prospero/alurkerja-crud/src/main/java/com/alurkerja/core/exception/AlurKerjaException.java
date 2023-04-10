package com.alurkerja.core.exception;

import lombok.Getter;

@Getter
public class AlurKerjaException extends Exception {
    private Integer errorCode;
    private String message;
    private String[] values;

    public AlurKerjaException(String message) {
        super(message);
        this.errorCode = 500;
        this.message = message;
    }

    public AlurKerjaException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;

    }

    public AlurKerjaException(Integer errorCode, String message, String... values) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.values = values;
    }
}
