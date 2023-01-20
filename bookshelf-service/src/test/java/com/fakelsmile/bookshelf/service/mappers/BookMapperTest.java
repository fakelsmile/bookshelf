package com.fakelsmile.bookshelf.service.mappers;

import com.fakelsmile.bookshelf.service.models.dto.BookDto;
import com.fakelsmile.bookshelf.service.models.dto.BookListDto;
import com.fakelsmile.bookshelf.service.models.dto.BookSaveUpdateDto;
import com.fakelsmile.bookshelf.service.models.dto.UserShortDto;
import com.fakelsmile.bookshelf.service.models.entity.BookEntity;
import com.fakelsmile.bookshelf.service.models.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class BookMapperTest {

    @InjectMocks
    private BookMapperImpl bookMapper;

    private BookEntity bookEntity;
    private BookDto bookDto;
    private BookSaveUpdateDto bookSaveUpdateDto;
    private UserEntity userEntity;
    private UserShortDto userShortDto;

    @BeforeEach
    public void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(3L);
        userEntity.setName("Rameshhh");

        userShortDto = new UserShortDto();
        userShortDto.setId(userEntity.getId());
        userShortDto.setName(userEntity.getName());

        bookEntity = BookEntity.builder()
                .id(3L)
                .name("Ramesh")
                .description("Fadatare")
                .fullText("ramesh@gmail.com")
                .views(0)
                .author(userEntity)
                .build();

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName("Ramesh");
        bookDto.setDescription("Fadatare");
        bookDto.setFullText("ramesh@gmail.com");
        bookDto.setViews(7);

        bookSaveUpdateDto = new BookSaveUpdateDto();
        bookSaveUpdateDto.setName(bookDto.getName());
        bookSaveUpdateDto.setDescription(bookDto.getDescription());
        bookSaveUpdateDto.setFullText(bookDto.getFullText());
        bookSaveUpdateDto.setAuthorId(userEntity.getId());
    }

    @DisplayName("JUnit test for map bookEntity to bookDto method")
    @Test
    void toDtoFromBookEntityTest() {
        BookDto expected = new BookDto();
        expected.setId(bookEntity.getId());
        expected.setName(bookEntity.getName());
        expected.setDescription(bookEntity.getDescription());
        expected.setFullText(bookEntity.getFullText());
        expected.setViews(bookEntity.getViews());
        expected.setAuthor(bookEntity.getAuthor().getId());

        BookDto result = bookMapper.toDto(bookEntity);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @DisplayName("JUnit test for map bookDto to bookEntity method")
    @Test
    void toEntityFromBookDtoTest() {
        BookEntity expected = new BookEntity();
        expected.setId(bookDto.getId());
        expected.setName(bookDto.getName());
        expected.setDescription(bookDto.getDescription());
        expected.setFullText(bookDto.getFullText());
        expected.setViews(bookDto.getViews());
        expected.setAuthor(userEntity);

        BookEntity actual = bookMapper.toEntity(bookDto);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @DisplayName("JUnit test for map bookEntity to toListDto method")
    @Test
    void toListDtoFromBookEntityTest() {
        BookListDto bookListDto = new BookListDto();
        bookListDto.setId(bookEntity.getId());
        bookListDto.setName(bookEntity.getName());
        bookListDto.setDescription(bookEntity.getDescription());
        bookListDto.setViews(bookEntity.getViews());
        bookListDto.setAuthor(userShortDto);

        List<BookListDto> expected = List.of(bookListDto);
        List<BookEntity> bookEntityList = List.of(bookEntity);

        List<BookListDto> actual = bookMapper.toListDto(bookEntityList);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expected, actual);
    }

    @DisplayName("JUnit test for map bookSaveUpdateDto to bookEntity method")
    @Test
    void toEntityFromSaveUpdateDtoTest() {
        BookEntity expected = new BookEntity();
        expected.setName(bookSaveUpdateDto.getName());
        expected.setDescription(bookSaveUpdateDto.getDescription());
        expected.setFullText(bookSaveUpdateDto.getFullText());
        expected.setAuthor(userEntity);

        BookEntity actual = bookMapper.toEntity(bookSaveUpdateDto);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
