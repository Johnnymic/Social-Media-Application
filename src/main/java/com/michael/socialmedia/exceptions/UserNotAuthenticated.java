package com.michael.socialmedia.exceptions;

public class UserNotAuthenticated extends RuntimeException {

    public UserNotAuthenticated(String message) {
        super(message);
    }
}
