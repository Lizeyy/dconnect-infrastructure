package com.dconnect.infrastructure.error;

public class InvitationNotFoundException extends RuntimeException {

    public InvitationNotFoundException() {}
    public InvitationNotFoundException(String message) {
        super(message);
    }
}
