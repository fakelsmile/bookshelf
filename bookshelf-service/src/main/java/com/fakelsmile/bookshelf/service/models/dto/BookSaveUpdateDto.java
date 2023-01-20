package com.fakelsmile.bookshelf.service.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for save and update book.
 */
@NoArgsConstructor
@Data
public class BookSaveUpdateDto {
    private String name;
    private String description;
    private String fullText;
    private Long authorId;
}
