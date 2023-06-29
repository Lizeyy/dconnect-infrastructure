package com.dconnect.infrastructure.error;

public class ConnectionNotFound extends IllegalArgumentException {

    public ConnectionNotFound(String message) {
        super(message);
    }
}
