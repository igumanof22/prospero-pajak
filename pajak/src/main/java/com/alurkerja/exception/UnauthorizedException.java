package com.alurkerja.exception;

public class UnauthorizedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -5896860997454921991L;

    private final String msg;

    public static UnauthorizedException create(String msg) {
        return new UnauthorizedException(msg);
    }

    public UnauthorizedException(String msg) {
        super();
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
