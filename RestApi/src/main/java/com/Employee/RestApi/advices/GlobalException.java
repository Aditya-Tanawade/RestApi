package com.Employee.RestApi.advices;

import com.Employee.RestApi.Exception.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<?>> handleResourceNotFound(ResourceNotFoundException exception){
        ApiError apiError=ApiError.builder().
                status(HttpStatus.NOT_FOUND).
                message(exception.getMessage() )
                .build();
        return  BuildAPIResponse(apiError);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleResourceNotFound(Exception exception){
        ApiError apiError=ApiError.builder().
                status(HttpStatus.INTERNAL_SERVER_ERROR).
                message(exception.getMessage() )
                .build();
        return   BuildAPIResponse(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleResourceNotFound(MethodArgumentNotValidException exception){
       List<String> errors= exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error ->error.getDefaultMessage())
               .collect(Collectors.toList());

        ApiError apiError=ApiError.builder().
                status(HttpStatus.BAD_REQUEST)
                .message("Input Validation Failed" )
                .subError(errors)
                .build();
        return  BuildAPIResponse(apiError);


    }

    private ResponseEntity<APIResponse<?>> BuildAPIResponse(ApiError apiError) {
        return new ResponseEntity<>(new APIResponse<>(apiError),apiError.getStatus());
    }
}
