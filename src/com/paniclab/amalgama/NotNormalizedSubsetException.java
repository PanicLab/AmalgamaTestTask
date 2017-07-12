package com.paniclab.amalgama;

/**
 * Created by root on 11.07.2017.
 */
public class NotNormalizedSubsetException extends RuntimeException {
    public NotNormalizedSubsetException() {
    }

    public NotNormalizedSubsetException(String message) {
        super(message);
    }

    public NotNormalizedSubsetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotNormalizedSubsetException(Throwable cause) {
        super(cause);
    }

    public NotNormalizedSubsetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
