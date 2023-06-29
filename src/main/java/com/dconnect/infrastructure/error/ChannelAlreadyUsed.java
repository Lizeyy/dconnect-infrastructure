package com.dconnect.infrastructure.error;

public class ChannelAlreadyUsed extends IllegalArgumentException {

    public ChannelAlreadyUsed(String message) {
        super(message);
    }
}
