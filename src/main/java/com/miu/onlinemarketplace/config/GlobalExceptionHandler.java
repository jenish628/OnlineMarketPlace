package com.miu.onlinemarketplace.config;


import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.exception.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleNotFoundException(DataNotFoundException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), 404, "Invalid User Input"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleValidationError(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(createErrorDetails("Validation Error", 400, ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    private ErrorDetails createErrorDetails(String message, Integer status, String type) {
        return ErrorDetails
                .builder()
                .message(message)
                .status(status)
                .type(type)
                .build();
    }
}
