package com.Employee.RestApi.Exception;

import com.Employee.RestApi.advices.GlobalException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){
        super(message);
    }
}
