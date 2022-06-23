package com.liudi.nettychat.config;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -5753329108039528485L;

    protected final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
