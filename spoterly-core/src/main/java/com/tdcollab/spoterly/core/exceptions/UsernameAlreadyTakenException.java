package com.tdcollab.spoterly.core.exceptions;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
