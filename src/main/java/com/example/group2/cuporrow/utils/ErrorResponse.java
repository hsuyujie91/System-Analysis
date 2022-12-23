package com.example.group2.cuporrow.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {
    public static ResponseEntity<String> notAuthorized() {
        return new ResponseEntity<String>("User not found", HttpStatus.FORBIDDEN);
    }

}
