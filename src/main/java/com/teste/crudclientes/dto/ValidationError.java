package com.teste.crudclientes.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError {

    private List<FieldMessage> errorMessages = new ArrayList<>();

    public ValidationError(
        Instant timestamp,
        Integer status,
        String error,
        String path
    ){
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getErrorMessages() {
        return errorMessages;
    }

    public void addErrorMessage(String fieldName, String message) {
        errorMessages.add(new FieldMessage(fieldName, message));
    }

}
