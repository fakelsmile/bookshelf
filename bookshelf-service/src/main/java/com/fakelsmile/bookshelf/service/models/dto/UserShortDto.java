package com.fakelsmile.bookshelf.service.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Short DTO for user.
 */
@NoArgsConstructor
@Data
public class UserShortDto {
    private Long id;
    private String name;
}
