package com.healthcare.helix.common.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}



//├── UserAlreadyExistsException.java
//├── UserNotFoundException.java
//├── InvalidCredentialsException.java
//├── InvalidTokenException.java
//├── TokenExpiredException.java
//├── RefreshTokenExpiredException.java
//├── OtpExpiredException.java
//├── OtpVerificationException.java
//├── EmailNotVerifiedException.java
//├── AccountDisabledException.java
//├── ResourceNotFoundException.java
//└── GlobalExceptionHandler.java