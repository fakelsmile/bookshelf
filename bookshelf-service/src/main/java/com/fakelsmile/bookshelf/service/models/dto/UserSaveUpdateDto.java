package com.fakelsmile.bookshelf.service.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO for save and update user.
 */
@NoArgsConstructor
@Data
public class UserSaveUpdateDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;
}

