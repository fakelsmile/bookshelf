package com.fakelsmile.bookshelf.service.models.dto;

import com.fakelsmile.bookshelf.service.models.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base DTO for user.
 */
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
