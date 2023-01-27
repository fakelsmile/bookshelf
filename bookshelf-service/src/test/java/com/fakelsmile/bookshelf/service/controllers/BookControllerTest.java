package com.fakelsmile.bookshelf.service.controllers;

import com.fakelsmile.bookshelf.service.TestUtil;
import com.fakelsmile.bookshelf.service.handlers.ErrorDto;
import com.fakelsmile.bookshelf.service.handlers.ErrorListResponse;
import com.fakelsmile.bookshelf.service.mappers.BookMapper;
import com.fakelsmile.bookshelf.service.models.dto.BookDto;
import com.fakelsmile.bookshelf.service.models.dto.BookListDto;
import com.fakelsmile.bookshelf.service.models.dto.BookSaveUpdateDto;
import com.fakelsmile.bookshelf.service.models.dto.UserShortDto;
import com.fakelsmile.bookshelf.service.models.entity.BookEntity;
import com.fakelsmile.bookshelf.service.models.entity.UserEntity;
import com.fakelsmile.bookshelf.service.services.BookService;
import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.fakelsmile.bookshelf.service.TestUtil.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private BookMapper bookMapper;

    private UserEntity userEntity;
    private BookEntity bookEntity;
    private BookEntity savedBookEntity;
    private BookSaveUpdateDto bookSaveUpdateDto;

    @BeforeEach
    public void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(2L);
        userEntity.setName("Rame");

        bookEntity = BookEntity.builder()
                .id(3L)
                .name("Ramesh")
                .description("Fadatare")
                .fullText("ramesh@gmail.com")
                .views(7)
                .author(userEntity)
                .build();

        savedBookEntity = BookEntity.builder()
                .id(3L)
                .name("Ramesh")
                .description("Fadatare")
                .fullText("ramesh@gmail.com")
                .views(0)
                .build();

        bookSaveUpdateDto = new BookSaveUpdateDto();
        bookSaveUpdateDto.setName("Ramesh");
        bookSaveUpdateDto.setDescription("Fadatare");
        bookSaveUpdateDto.setFullText("ramesh@gmail.com");
    }

    @AfterEach
    public void turnDown() {
        verifyNoMoreInteractions(bookMapper, bookService);
    }

    @DisplayName("JUnit test for getAllBooks method")
    @Test
    public void getAllBooksTest() throws Exception {
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(userEntity.getId());
        userShortDto.setName(userEntity.getName());

        BookListDto bookListDto = new BookListDto();
        bookListDto.setId(3L);
        bookListDto.setName("Ramesh");
        bookListDto.setDescription("Fadatare");
        bookListDto.setViews(7);
        bookListDto.setAuthor(userShortDto);

        List<BookEntity> bookEntityList = List.of(bookEntity);

        List<BookListDto> expected = List.of(bookListDto);

        when(bookService.getAllBooks()).thenReturn(bookEntityList);
        when(bookMapper.toListDto(bookEntityList)).thenReturn(expected);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        List<BookListDto> actual = TestUtil.objectMapper.readValue(jsonResult, new TypeReference<>() { });

        assertEquals(expected, actual);
        verify(bookService).getAllBooks();
        verify(bookMapper).toListDto(bookEntityList);
    }

    @DisplayName("JUnit test for addBook method")
    @Test
    public void addBookTest() throws Exception {
        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setName("Ramesh");
        expected.setDescription("Fadatare");
        expected.setFullText("ramesh@gmail.com");
        expected.setViews(7);

        when(bookMapper.toEntity(bookSaveUpdateDto)).thenReturn(bookEntity);
        when(bookService.saveBook(bookEntity)).thenReturn(savedBookEntity);
        when(bookMapper.toDto(savedBookEntity)).thenReturn(expected);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/books")
                        .content(asJsonString(bookSaveUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        BookDto actual = TestUtil.objectMapper.readValue(jsonResult, BookDto.class);

        assertEquals(expected, actual);
        verify(bookMapper).toEntity(bookSaveUpdateDto);
        verify(bookService).saveBook(bookEntity);
        verify(bookMapper).toDto(savedBookEntity);
    }

    @DisplayName("Should return the HTTP status code bad request (400) if Name is null")
    @Test
    void addBookWithNameIsNullTest() throws Exception {
        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setName(null);
        expected.setDescription("Fadatare");
        expected.setFullText("ramesh@gmail.com");
        expected.setViews(7);

        ErrorDto errorDto = new ErrorDto("name", "Name cannot be empty");
        ErrorListResponse error = new ErrorListResponse(List.of(errorDto));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/books")
                        .content(asJsonString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ErrorListResponse actual = TestUtil.objectMapper.readValue(jsonResult, ErrorListResponse.class);
        assertEquals(error, actual);
    }

    @DisplayName("Should return the HTTP status code bad request (400) if Description is null")
    @Test
    void addBookWithDescriptionIsNullTest() throws Exception {
        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setName("Ramesh");
        expected.setDescription(null);
        expected.setFullText("ramesh@gmail.com");
        expected.setViews(7);

        ErrorDto errorDto = new ErrorDto("description", "Description cannot be empty");
        ErrorListResponse error = new ErrorListResponse(List.of(errorDto));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/books")
                        .content(asJsonString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ErrorListResponse actual = TestUtil.objectMapper.readValue(jsonResult, ErrorListResponse.class);
        assertEquals(error, actual);
    }

    @DisplayName("Should return the HTTP status code bad request (400) if FullText is null")
    @Test
    void addBookWithFullTextIsNullTest() throws Exception {
        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setName("Ramesh");
        expected.setDescription("Fadatare");
        expected.setFullText(null);
        expected.setViews(7);

        ErrorDto errorDto = new ErrorDto("fullText", "FullText cannot be empty");
        ErrorListResponse error = new ErrorListResponse(List.of(errorDto));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/books")
                        .content(asJsonString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ErrorListResponse actual = TestUtil.objectMapper.readValue(jsonResult, ErrorListResponse.class);
        assertEquals(error, actual);
    }

    @DisplayName("JUnit test for getBook method")
    @Test
    public void getBookTest() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setName("Ramesh");
        bookDto.setDescription("Fadatare");
        bookDto.setFullText("ramesh@gmail.com");
        bookDto.setViews(7);

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setName("Ramesh");
        expected.setDescription("Fadatare");
        expected.setFullText("ramesh@gmail.com");
        expected.setViews(7);

        when(bookService.getBook(bookEntity.getId())).thenReturn(bookEntity);
        when(bookMapper.toDto(bookEntity)).thenReturn(expected);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/books/" + bookEntity.getId())
                        .content(asJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        BookDto actual = TestUtil.objectMapper.readValue(jsonResult, BookDto.class);

        assertEquals(expected, actual);
        verify(bookService).getBook(bookEntity.getId());
        verify(bookMapper).toDto(bookEntity);
    }

    @DisplayName("JUnit test for updateBook method")
    @Test
    public void updateBookTest() throws Exception {
        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setName("Ramesh");
        expected.setDescription("Fadatare");
        expected.setFullText("ramesh@gmail.com");
        expected.setViews(7);

        when(bookMapper.toEntity(bookSaveUpdateDto)).thenReturn(bookEntity);
        when(bookService.updateBook(bookEntity.getId(), bookEntity)).thenReturn(savedBookEntity);
        when(bookMapper.toDto(savedBookEntity)).thenReturn(expected);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/books/" + bookEntity.getId())
                        .content(asJsonString(bookSaveUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        BookDto actual = TestUtil.objectMapper.readValue(jsonResult, BookDto.class);

        assertEquals(expected, actual);
        verify(bookMapper).toEntity(bookSaveUpdateDto);
        verify(bookService).updateBook(bookEntity.getId(), bookEntity);
        verify(bookMapper).toDto(savedBookEntity);
    }

    @DisplayName("JUnit test for deleteBook method")
    @Test
    public void deleteBookTest() throws Exception {
        long bookId = 3L;

        doNothing().when(bookService).deleteBook(bookId);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/books/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService).deleteBook(bookId);
    }
}