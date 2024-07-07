package ru.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(EntityNotFoundException exception) {
        UserErrorResponse response = new UserErrorResponse(exception.getMessage(),
                System.currentTimeMillis());
        //В HTTP ответе тело будет =response и статус в заголовке 404
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException exception) {
        UserErrorResponse response = new UserErrorResponse(exception.getMessage(),
                System.currentTimeMillis());
        //В HTTP ответе тело будет =response и статус в заголовке 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotUpdateException exception) {
        UserErrorResponse response = new UserErrorResponse(exception.getMessage(),
                System.currentTimeMillis());
        //В HTTP ответе тело будет =response и статус в заголовке 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(Exception exception) {
        UserErrorResponse response = new UserErrorResponse(exception.getMessage(),
                System.currentTimeMillis());
        //В HTTP ответе тело будет =response и статус в заголовке 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
