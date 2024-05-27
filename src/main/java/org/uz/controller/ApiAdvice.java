package org.uz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.uz.model.response.BaseResponse;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> globalException(Exception ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        BaseResponse response = new BaseResponse();
        response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        response.setErrors(errorDetails);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<?> forbiddenException(HttpClientErrorException.Forbidden ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        BaseResponse response = new BaseResponse();
        response.setCode(String.valueOf(HttpStatus.FORBIDDEN));
        response.setErrors(errorDetails);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<?> badRequestException(HttpClientErrorException.BadRequest ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        BaseResponse response = new BaseResponse();
        response.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
        response.setErrors(errorDetails);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
