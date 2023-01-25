package com.fakelsmile.bookshelf.service.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Base DTO for book.
 */
@NoArgsConstructor
@Data
public class BookDto {
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotBlank(message = "FullText cannot be empty")
    private String fullText;
    private Integer views;
    private Long author;
}
