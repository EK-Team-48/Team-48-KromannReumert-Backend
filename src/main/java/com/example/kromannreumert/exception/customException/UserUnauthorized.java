package com.example.kromannreumert.exception.customException;

public class UserUnauthorized extends ForbiddenException {
    /**
     * Creates a UserUnauthorized exception with the predefined message "Invalid credentials".
     */
    public UserUnauthorized() {
        super("Invalid credentials");
    }
}