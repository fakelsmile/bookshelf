package com.fakelsmile.bookshelf.service.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base DTO for book.
 */
@NoArgsConstructor
@Data
public class BookDto {
    private Long id;
    private String name;
    private String description;
    private String fullText;
    private Integer views;
    private Long author;
}
