package org.miracle.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userLogin) {
        super("User with login " + userLogin + " not found");
    }
}
