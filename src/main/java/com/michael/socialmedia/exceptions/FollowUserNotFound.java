package com.michael.socialmedia.exceptions;

public class FollowUserNotFound  extends  RuntimeException{

    public FollowUserNotFound(String message) {
        super(message);
    }
}
