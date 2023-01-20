package com.fakelsmile.bookshelf.service.errors;

/**
 * Exception when user is not found by id
 */
public class UserNotFoundException extends Exception{
    public UserNotFoundException(long id) {
        super("User not found by id: " + id);
    }
}