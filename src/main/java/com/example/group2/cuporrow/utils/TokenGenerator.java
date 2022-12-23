package com.example.group2.cuporrow.utils;

import java.util.UUID;

public class TokenGenerator {
    public static String generate() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}