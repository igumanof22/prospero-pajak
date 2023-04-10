package com.alurkerja.exception;

public class ResourceForbiddenException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 8720716765345933158L;

    private final String msg;

    public static ResourceForbiddenException create(String msg) {
        return new ResourceForbiddenException(msg);
    }

    public ResourceForbiddenException(String msg) {
        super();
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
