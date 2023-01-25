package com.fakelsmile.bookshelf.service.models.dto;

import com.fakelsmile.bookshelf.service.models.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Base DTO for user.
 */
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    private UserRole role;
}
