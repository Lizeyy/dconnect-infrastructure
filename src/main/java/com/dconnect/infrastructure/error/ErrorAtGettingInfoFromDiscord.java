package com.dconnect.infrastructure.error;

public class ErrorAtGettingInfoFromDiscord extends IllegalArgumentException {

    public ErrorAtGettingInfoFromDiscord(String message) {
        super(message);
    }
}
