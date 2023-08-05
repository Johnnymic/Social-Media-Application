package com.michael.socialmedia.exceptions;

import com.michael.socialmedia.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotAuthenticated.class)
    private ResponseEntity<com.michael.socialmedia.dto.response.ErrorResponse> UserNotFoundFoundException(UserNotAuthenticated userNotAuthenticated,
                                                                                                          WebRequest webRequest){
        com.michael.socialmedia.dto.response.ErrorResponse errorResponse = new ErrorResponse(new Date(), userNotAuthenticated.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostNotfoundException.class)
    private ResponseEntity<ErrorResponse>productNotFoundException(PostNotfoundException postNotfoundException,
                                                                  WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), postNotfoundException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(PostNotfoundException.class)
    private ResponseEntity<ErrorResponse>productNotFoundException(WrongPasswordException wrongPasswordException,
                                                                  WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), wrongPasswordException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(PostNotfoundException.class)
    private ResponseEntity<ErrorResponse>productNotFoundException(CommentNotFoundException commentNotFoundException, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), commentNotFoundException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(AccountDetailException.class)
    private ResponseEntity<ErrorResponse>productNotFoundException(AccountDetailException accountDetailException, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), accountDetailException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);

    }











}
