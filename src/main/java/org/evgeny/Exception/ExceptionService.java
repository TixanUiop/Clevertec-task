package org.evgeny.Exception;

public class ExceptionService extends RuntimeException {
    public ExceptionService(String message) {
        super(message);
    }
}
