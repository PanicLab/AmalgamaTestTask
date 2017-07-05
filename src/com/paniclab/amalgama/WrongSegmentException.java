package com.paniclab.amalgama;

/**
 * Created by Сергей on 04.07.2017.
 */
public class WrongSegmentException extends RuntimeException {
    public WrongSegmentException() {
    }

    public WrongSegmentException(String message) {
        super(message);
    }

    public WrongSegmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongSegmentException(Throwable cause) {
        super(cause);
    }

    public WrongSegmentException(String message,
                                 Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
                                            super(message, cause, enableSuppression, writableStackTrace);
    }
}
