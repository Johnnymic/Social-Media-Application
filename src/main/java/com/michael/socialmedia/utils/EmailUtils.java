package com.michael.socialmedia.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Security;

public class EmailUtils {
    public  static String getEmailFromContent(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
