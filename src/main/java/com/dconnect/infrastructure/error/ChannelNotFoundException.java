package com.dconnect.infrastructure.error;

public class ChannelNotFoundException extends RuntimeException {

    public ChannelNotFoundException() {}
    public ChannelNotFoundException(String message) {
        super(message);
    }
}
