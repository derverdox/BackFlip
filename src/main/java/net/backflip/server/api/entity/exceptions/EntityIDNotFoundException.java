package net.backflip.server.api.entity.exceptions;

public class EntityIDNotFoundException extends RuntimeException{
    public EntityIDNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
    public EntityIDNotFoundException(String message){
        super(message);
    }
}