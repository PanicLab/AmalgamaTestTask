package com.paniclab.amalgama;

/**
 * Created by Сергей on 04.07.2017.
 */
public class ZeroLengthSegmentException extends RuntimeException {
    public ZeroLengthSegmentException() {
    }

    public ZeroLengthSegmentException(String message) {
        super(message);
    }

    public ZeroLengthSegmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZeroLengthSegmentException(Throwable cause) {
        super(cause);
    }

    public ZeroLengthSegmentException(String message,
                                      Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
                                            super(message, cause, enableSuppression, writableStackTrace);
    }
}
