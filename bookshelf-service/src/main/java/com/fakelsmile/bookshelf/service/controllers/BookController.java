package com.fakelsmile.bookshelf.service.controllers;

import com.fakelsmile.bookshelf.service.errors.BookNotFoundException;
import com.fakelsmile.bookshelf.service.mappers.BookMapper;
import com.fakelsmile.bookshelf.service.models.dto.BookDto;
import com.fakelsmile.bookshelf.service.models.dto.BookListDto;
import com.fakelsmile.bookshelf.service.models.dto.BookSaveUpdateDto;
import com.fakelsmile.bookshelf.service.models.entity.BookEntity;
import com.fakelsmile.bookshelf.service.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for books.
 */
@Validated
@RestController
@RequestMapping(path = "/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;
    private final BookMapper mapper;

    /**
     * Get all the books.
     *
     * @return list of books
     */
    @GetMapping
    List<BookListDto> getAllBooks() {
        List<BookEntity> bookEntityList = service.getAllBooks();
        return mapper.toListDto(bookEntityList);
    }

    /**
     * Create a new book.
     *
     * @param bookSaveUpdateDto - book for create
     * @return created book
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BookDto createBook(@Valid @RequestBody BookSaveUpdateDto bookSaveUpdateDto) {
        BookEntity bookEntity = mapper.toEntity(bookSaveUpdateDto);
        bookEntity = service.saveBook(bookEntity);
        return mapper.toDto(bookEntity);
    }

    /**
     * Get a book by id.
     *
     * @param id - book id
     * @return found book
     * @throws BookNotFoundException if the book is not found
     */
    @GetMapping("/{id}")
    BookDto getBook(@PathVariable Long id) throws BookNotFoundException {
        BookEntity bookEntityId = service.getBook(id);
        return mapper.toDto(bookEntityId);
    }

    /**
     * Update a book by id.
     *
     * @param id - book id
     * @param bookSaveUpdateDto - book for update
     * @return updated book
     * @throws BookNotFoundException if the book is not found
     */
    @PutMapping("/{id}")
    BookDto updateBook(@Valid @RequestBody BookSaveUpdateDto bookSaveUpdateDto, @PathVariable Long id) throws BookNotFoundException {
        BookEntity bookEntity = mapper.toEntity(bookSaveUpdateDto);
        bookEntity = service.updateBook(id, bookEntity);
        return mapper.toDto(bookEntity);
    }

    /**
     * Delete a book by id.
     *
     * @param id - book id
     */
    @DeleteMapping("/{id}")
    void deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
    }
}
