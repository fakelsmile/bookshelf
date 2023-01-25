package com.fakelsmile.bookshelf.service.controllers;

import com.fakelsmile.bookshelf.service.errors.UserNotFoundException;
import com.fakelsmile.bookshelf.service.mappers.UserMapper;
import com.fakelsmile.bookshelf.service.models.dto.UserDto;
import com.fakelsmile.bookshelf.service.models.entity.UserEntity;
import com.fakelsmile.bookshelf.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for users.
 */
@Validated
@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    /**
     * Get all the users.
     *
     * @return list of users
     */
    @GetMapping
    List<UserDto> getAllUsers() {
        List<UserEntity> userEntityList = service.getAllUsers();
        return userEntityList.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /**
     * Create a new user.
     *
     * @param userDto - user
     * @return created user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(@Valid @RequestBody UserDto userDto) {
        UserEntity userEntity = mapper.toEntity(userDto);
        userEntity = service.saveUser(userEntity);
        return mapper.toDto(userEntity);
    }

    /**
     * Get a user by id.
     *
     * @param id - user id
     * @return found user
     * @throws UserNotFoundException if the user is not found
     */
    @GetMapping("/{id}")
    UserDto getUser(@PathVariable Long id) throws UserNotFoundException {
        UserEntity userEntityId = service.getUser(id);
        return mapper.toDto(userEntityId);
    }

    /**
     * Update a user by id.
     *
     * @param id - user id
     * @param userDto - user
     * @return updated user
     * @throws UserNotFoundException if the user is not found
     */
    @PutMapping("/{id}")
    UserDto updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long id) throws UserNotFoundException {
        UserEntity userEntity = mapper.toEntity(userDto);
        userEntity = service.updateUser(id, userEntity);
        return mapper.toDto(userEntity);
    }

    /**
     * Delete a user by id.
     *
     * @param id - user id
     */
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }
}
