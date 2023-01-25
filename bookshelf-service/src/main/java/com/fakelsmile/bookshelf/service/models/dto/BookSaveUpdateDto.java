package com.fakelsmile.bookshelf.service.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * DTO for save and update book.
 */
@NoArgsConstructor
@Data
public class BookSaveUpdateDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotBlank(message = "FullText cannot be empty")
    private String fullText;
    private Long authorId;
}
