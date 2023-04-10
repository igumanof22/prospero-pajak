package com.alurkerja.exception;

public class ResourceConflictException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -5896860997454921991L;

    private final String msg;

    public static ResourceConflictException create(String msg) {
        return new ResourceConflictException(msg);
    }

    public ResourceConflictException(String msg) {
        super();
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
