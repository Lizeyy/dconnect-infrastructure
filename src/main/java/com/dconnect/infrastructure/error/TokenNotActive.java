package com.dconnect.infrastructure.error;

public class TokenNotActive extends IllegalArgumentException {

    public TokenNotActive(String message) {
        super(message);
    }
}
