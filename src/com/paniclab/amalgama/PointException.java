package com.paniclab.amalgama;

/**
 * Created by Сергей on 08.07.2017.
 */
public class PointException extends RuntimeException {
    public PointException() {
    }

    public PointException(String message) {
        super(message);
    }

    public PointException(String message, Throwable cause) {
        super(message, cause);
    }

    public PointException(Throwable cause) {
        super(cause);
    }

    public PointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
