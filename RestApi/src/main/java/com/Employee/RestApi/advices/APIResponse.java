package com.Employee.RestApi.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class APIResponse <T>{

    @JsonFormat(pattern = "hh:mm:ss  dd-MM-yyyy")
    private LocalDateTime timestamp;
    private  T data;
    private  ApiError error;

    public APIResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public APIResponse(ApiError error) {
        this();
        this.error = error;
    }

    public APIResponse(T data) {
        this();
        this.data = data;
    }
}
