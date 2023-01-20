package com.fakelsmile.bookshelf.service.errors;

/**
 * Exception when book is not found by id
 */
public class BookNotFoundException extends Exception{
    public BookNotFoundException(long id) {
        super("Book not found by id: " + id);
    }
}