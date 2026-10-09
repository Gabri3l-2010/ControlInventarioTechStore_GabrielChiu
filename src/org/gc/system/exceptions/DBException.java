package org.gc.system.exceptions;

public class DBException extends AppException {
    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
