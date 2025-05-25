package com.aieta.springboot.todo_app.config.exception;

public class EmailAlreadyOnUseException extends RuntimeException {
    public EmailAlreadyOnUseException(String message) {
        super(message);
    }
}
