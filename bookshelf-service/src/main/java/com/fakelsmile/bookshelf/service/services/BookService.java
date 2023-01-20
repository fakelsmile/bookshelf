package com.fakelsmile.bookshelf.service.services;

import com.fakelsmile.bookshelf.service.errors.BookNotFoundException;
import com.fakelsmile.bookshelf.service.models.entity.BookEntity;
import com.fakelsmile.bookshelf.service.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for books.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Get all the books.
     *
     * @return list of books
     */
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Save a new book.
     *
     * @param book - book
     * @return saved book
     */
    public BookEntity saveBook(BookEntity book) {
        book = bookRepository.save(book);
        return book;
    }

    /**
     * Get a book by id.
     *
     * @param id - book id
     * @return found book
     * @throws BookNotFoundException if the book is not found
     */
    public BookEntity getBook(long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    /**
     * Update a book by id.
     *
     * @param id - book id
     * @param book book
     * @return updated book
     * @throws BookNotFoundException if the book is not found
     */
    public BookEntity updateBook(long id,
                                 BookEntity book) throws BookNotFoundException {

        BookEntity foundBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        foundBook.setName(book.getName());
        foundBook.setDescription(book.getDescription());
        foundBook.setFullText(book.getFullText());
        foundBook = saveBook(foundBook);
        return foundBook;
    }

    /**
     * Delete a book by id.
     *
     * @param id - book id
     */
    public void deleteBook(long id) {
        try {
            bookRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        }
    }
}
