package com.dconnect.infrastructure.error;

public class InvitationAlreadyExist extends IllegalArgumentException {

    public InvitationAlreadyExist(String message) {
        super(message);
    }
}
