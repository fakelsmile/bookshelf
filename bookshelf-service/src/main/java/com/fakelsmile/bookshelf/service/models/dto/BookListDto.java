package com.fakelsmile.bookshelf.service.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for book in list.
 */
@NoArgsConstructor
@Data
public class BookListDto {
    private Long id;
    private String name;
    private String description;
    private Integer views;
    private UserShortDto author;
}
