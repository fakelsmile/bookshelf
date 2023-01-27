package com.fakelsmile.bookshelf.service.handlers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Base DTO for error.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorDto {
    private String fieldName;
    private String message;
}
