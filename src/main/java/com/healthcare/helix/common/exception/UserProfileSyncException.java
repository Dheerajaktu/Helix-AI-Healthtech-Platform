package com.healthcare.helix.common.exception;

public class UserProfileSyncException extends RuntimeException {
    public UserProfileSyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
