package com.fakelsmile.bookshelf.service.mappers;

import com.fakelsmile.bookshelf.service.models.dto.BookDto;
import com.fakelsmile.bookshelf.service.models.dto.BookListDto;
import com.fakelsmile.bookshelf.service.models.dto.BookSaveUpdateDto;
import com.fakelsmile.bookshelf.service.models.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for books.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    /**
     * Map BookDto to BookEntity.
     *
     * @param bookDto - book DTO
     * @return mapped bookEntity
     */
    @Mapping(source = "author", target = "author.id")
    BookEntity toEntity(BookDto bookDto);

    /**
     * Map BookEntity to BookDto.
     *
     * @param bookEntity - book entity
     * @return mapped bookDto
     */
    @Mapping(source = "author.id", target = "author")
    BookDto toDto(BookEntity bookEntity);

    /**
     * Map bookEntity to List of BookListDto.
     *
     * @param bookEntity - book entity
     * @return mapped BookListDto
     */
    BookListDto toListDto(BookEntity bookEntity);

    /**
     * Map List of BookEntities to List of BookListDto.
     *
     * @param bookEntity - book entity
     * @return mapped BookListDto
     */
    List<BookListDto> toListDto(List<BookEntity> bookEntity);

    /**
     * Map BookSaveUpdateDto to BookEntity.
     *
     * @param bookSaveUpdateDto - DTO for save and update book
     * @return mapped BookEntity
     */
    @Mapping(source = "authorId", target = "author.id")
    BookEntity toEntity(BookSaveUpdateDto bookSaveUpdateDto);
}
