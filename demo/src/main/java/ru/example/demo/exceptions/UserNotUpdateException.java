package ru.example.demo.exceptions;

public class UserNotUpdateException extends RuntimeException{
    public UserNotUpdateException(String message) {
        super(message);
    }
}
