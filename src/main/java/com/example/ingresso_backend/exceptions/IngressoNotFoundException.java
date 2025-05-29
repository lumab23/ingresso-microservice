package com.example.ingresso_backend.exceptions;

public class IngressoNotFoundException extends RuntimeException {
    public IngressoNotFoundException(String message) {
        super(message);
    }
}
