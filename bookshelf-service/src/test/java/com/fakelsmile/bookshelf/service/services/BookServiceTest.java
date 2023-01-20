package com.fakelsmile.bookshelf.service.services;

import com.fakelsmile.bookshelf.service.errors.BookNotFoundException;
import com.fakelsmile.bookshelf.service.models.entity.BookEntity;
import com.fakelsmile.bookshelf.service.repo.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    private BookEntity book;

    private BookEntity savedBook;

    @BeforeEach
    public void setup() {
        book = BookEntity.builder()
                .name("Ramesh")
                .description("Fadatare")
                .fullText("ramesh@gmail.com")
                .build();

        savedBook = BookEntity.builder()
                .id(1L)
                .name("Ramesh")
                .description("Fadatare")
                .fullText("ramesh@gmail.com")
                .views(0)
                .build();
    }

    @AfterEach
    public void turnDown() {
        verifyNoMoreInteractions(bookRepository);
    }

    @DisplayName("JUnit test for saveBook method")
    @Test
    public void saveBookTest() {
        when(bookRepository.save(book)).thenReturn(savedBook);
        BookEntity actual = bookService.saveBook(book);

        assertEquals(savedBook, actual);
        verify(bookRepository).save(book);
    }

    @DisplayName("JUnit test for getAllBooks method")
    @Test
    public void getAllBooksTest() {
        when(bookRepository.findAll()).thenReturn(List.of(book, savedBook));
        List<BookEntity> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals(book, books.get(0));
        assertEquals(savedBook, books.get(1));
        verify(bookRepository).findAll();
    }

    @DisplayName("JUnit test for getBook method")
    @Test
    public void getBookTest() throws BookNotFoundException {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(savedBook));
        BookEntity actual = bookService.getBook(savedBook.getId());

        assertEquals(savedBook, actual);
        verify(bookRepository).findById(1L);
    }

    @DisplayName("JUnit test for updateBook method")
    @Test
    public void updateBookTest() throws BookNotFoundException {
        BookEntity bookForUpdate = BookEntity.builder()
                .id(1L)
                .name("Rh")
                .description("Fare")
                .fullText("rameshil.com")
                .views(0)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookForUpdate));
        when(bookRepository.save(savedBook)).thenReturn(savedBook);

        BookEntity actual = bookService.updateBook(savedBook.getId(), savedBook);

        assertEquals(savedBook, actual);
        verify(bookRepository).findById(1L);
        verify(bookRepository).save(savedBook);
    }

    @DisplayName("JUnit test for deleteBook method")
    @Test
    public void deleteBookTest() {
        long bookId = 1L;
        willDoNothing().given(bookRepository).deleteById(bookId);
        bookService.deleteBook(bookId);

        verify(bookRepository).deleteById(bookId);
    }

    @DisplayName("JUnit test for updateBook method with error")
    @Test
    public void updateBookWithErrorTest() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(savedBook.getId(), savedBook));

        verify(bookRepository).findById(1L);
    }

    @DisplayName("JUnit test for getBook method with error")
    @Test
    public void getBookWithErrorTest() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBook(savedBook.getId()));

        verify(bookRepository).findById(1L);
    }
}
