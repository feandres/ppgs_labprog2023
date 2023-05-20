package br.ufma.sppg.service.exceptions;

public class CustomMessageRuntimeException extends RuntimeException{
    public CustomMessageRuntimeException(String message){
        super(message);
    }
}
