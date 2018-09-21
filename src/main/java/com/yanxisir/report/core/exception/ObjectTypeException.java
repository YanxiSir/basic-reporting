package com.yanxisir.report.core.exception;

/**
 * author: yanxi
 * date : 16/12/7
 */
public class ObjectTypeException extends RuntimeException {
    public ObjectTypeException() {
        super();
    }

    public ObjectTypeException(String message) {
        super(message);
    }

    public ObjectTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectTypeException(Throwable cause) {
        super(cause);
    }
}
