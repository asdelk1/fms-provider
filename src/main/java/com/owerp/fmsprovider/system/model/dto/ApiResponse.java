package com.owerp.fmsprovider.system.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiResponse {

    private HttpStatus status;
    private String message;
    private Object data;

    public ApiResponse(){}

    public ApiResponse(HttpStatus status){
        this.status = status;
    }

    public ApiResponse(HttpStatus status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
