package com.teste.crudclientes.controllers.handlers;

import com.teste.crudclientes.dto.CustomError;
import com.teste.crudclientes.dto.ValidationError;
import com.teste.crudclientes.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionsHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(
        ResourceNotFoundException exception,
        HttpServletRequest request
    ){

        var status = HttpStatus.NOT_FOUND;
        var err = new CustomError(
            Instant.now(),
            status.value(),
            exception.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidation(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ){
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        var err = new ValidationError(
            Instant.now(), status.value(), "Dados inválidos", request.getRequestURI()
        );
        for(FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            err.addErrorMessage(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

}
