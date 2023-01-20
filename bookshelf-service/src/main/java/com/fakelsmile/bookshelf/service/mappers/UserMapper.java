package com.fakelsmile.bookshelf.service.mappers;


import com.fakelsmile.bookshelf.service.models.dto.UserDto;
import com.fakelsmile.bookshelf.service.models.dto.UserListDto;
import com.fakelsmile.bookshelf.service.models.dto.UserSaveUpdateDto;
import com.fakelsmile.bookshelf.service.models.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Mapper for users.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Map UserDto to UserEntity.
     *
     * @param userDto - user DTO
     * @return mapped userEntity
     */
    @Mappings({
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "password", source = "password")})
    UserEntity toEntity(UserDto userDto);

    /**
     * Map UserEntity to List of  UserListDto.
     *
     * @param userEntity - user entity
     * @return mapped UserListDto
     */
    @Mapping(target = "booksCount", expression = "java( userEntity.getBooks().size() )")
    UserListDto toListDto(UserEntity userEntity);

    /**
     * Map List of UserEntities to List of UserListDto.
     *
     * @param userEntity - user entity
     * @return mapped UserListDto
     */
    List<UserListDto> toListDto(List<UserEntity> userEntity);

    /**
     * Map UserEntity to UserDto.
     *
     * @param userEntity - user entity
     * @return mapped userDto
     */
    @Mappings({
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "password", source = "password")})
    UserDto toDto(UserEntity userEntity);

    /**
     * Map UserSaveUpdateDto to UserEntity.
     *
     * @param userSaveUpdateDto - DTO for save and update user
     * @return mapped UserEntity
     */
    UserEntity toEntity(UserSaveUpdateDto userSaveUpdateDto);
}
