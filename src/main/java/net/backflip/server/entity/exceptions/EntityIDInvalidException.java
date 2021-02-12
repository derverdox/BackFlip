package net.backflip.server.entity.exceptions;

public class EntityIDInvalidException extends RuntimeException {
    public EntityIDInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityIDInvalidException(String message) {
        super(message);
    }
}
