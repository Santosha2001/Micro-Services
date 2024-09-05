package com.user.service.exception;

import com.user.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * @RestControllerAdvice -> is to specify that this will handle exceptions to the project.
 * */
@RestControllerAdvice
public class GlobalExceptionHandler {


    /*
     * @ResponseEntity -> sending data along with status code.
     * @ExceptionHandler -> will handle the exception of the specific type anywhere in the project.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {

        String message = exception.getMessage();
        ApiResponse response = ApiResponse.builder().message(message).success(true).httpStatus(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
    }
}
