package com.teste.crudclientes.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException (String message)  {
        super(message);
    }

}
