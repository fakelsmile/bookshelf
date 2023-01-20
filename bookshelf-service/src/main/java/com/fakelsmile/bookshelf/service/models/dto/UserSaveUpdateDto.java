package com.fakelsmile.bookshelf.service.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for save and update user.
 */
@NoArgsConstructor
@Data
public class UserSaveUpdateDto {
    private String name;
    private String email;
}
