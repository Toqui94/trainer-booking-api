package com.trainer.trainer_booking_api.exception;

public class RecursoNoEncontradoException extends RuntimeException {
    
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}