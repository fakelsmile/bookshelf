package com.fakelsmile.bookshelf.service.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user in list.
 */
@NoArgsConstructor
@Data
public class UserListDto {
    private Long id;
    private String name;
    private Integer booksCount;
}
