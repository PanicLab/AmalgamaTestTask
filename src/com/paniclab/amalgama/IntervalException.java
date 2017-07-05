package com.paniclab.amalgama;

/**
 * Created by Сергей on 04.07.2017.
 */
public class IntervalException extends RuntimeException {
    public IntervalException() {
    }

    public IntervalException(String message) {
        super(message);
    }

    public IntervalException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntervalException(Throwable cause) {
        super(cause);
    }

    public IntervalException(String message,
                             Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
                                            super(message, cause, enableSuppression, writableStackTrace);
    }
}
