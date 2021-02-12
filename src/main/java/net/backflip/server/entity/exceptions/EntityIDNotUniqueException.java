package net.backflip.server.entity.exceptions;

public class EntityIDNotUniqueException extends RuntimeException{
    public EntityIDNotUniqueException(String message, Throwable cause){
        super(message,cause);
    }
    public EntityIDNotUniqueException(String message){
        super(message);
    }
}
