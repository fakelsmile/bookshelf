package com.fakelsmile.bookshelf.service.mappers;

import com.fakelsmile.bookshelf.service.models.UserRole;
import com.fakelsmile.bookshelf.service.models.dto.UserDto;
import com.fakelsmile.bookshelf.service.models.dto.UserListDto;
import com.fakelsmile.bookshelf.service.models.dto.UserSaveUpdateDto;
import com.fakelsmile.bookshelf.service.models.dto.UserShortDto;
import com.fakelsmile.bookshelf.service.models.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapperImpl userMapper;

    private UserEntity userEntity;
    private UserDto userDto;
    private UserSaveUpdateDto userSaveUpdateDto;

    @BeforeEach
    public void setUp() {
        userEntity = UserEntity.builder()
                .id(1L)
                .name("Zaph")
                .email("Zaph@gmail")
                .password("qwerty123")
                .role(UserRole.USER)
                .books(List.of())
                .build();

        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(userEntity.getId());
        userShortDto.setName(userEntity.getName());

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Zaph");
        userDto.setEmail("Zaph@gmail");
        userDto.setPassword("qwerty123");
        userDto.setRole(UserRole.USER);

        userSaveUpdateDto = new UserSaveUpdateDto();
        userSaveUpdateDto.setName(userDto.getName());
        userSaveUpdateDto.setEmail(userDto.getEmail());
    }

    @DisplayName("JUnit test for map userEntity to userDto")
    @Test
    void toDtoFromUserEntityTest() {
        UserDto expected = new UserDto();
        expected.setId(userEntity.getId());
        expected.setName(userEntity.getName());
        expected.setEmail(userEntity.getEmail());
        expected.setPassword(userEntity.getPassword());
        expected.setRole(userEntity.getRole());

        UserDto result = userMapper.toDto(userEntity);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @DisplayName("JUnit test for map userDto to userEntity")
    @Test
    void toEntityFromUserDtoTest() {
        UserEntity expected = new UserEntity();
        expected.setId(userDto.getId());
        expected.setName(userDto.getName());
        expected.setEmail(userDto.getEmail());
        expected.setPassword(userDto.getPassword());
        expected.setRole(userDto.getRole());

        UserEntity actual = userMapper.toEntity(userDto);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @DisplayName("JUnit test for map userEntity to toListDto")
    @Test
    void toListDtoFromUserEntityTest() {
        UserListDto userListDto = new UserListDto();
        userListDto.setId(userEntity.getId());
        userListDto.setName(userEntity.getName());
        userListDto.setBooksCount(userEntity.getBooks().size());

        List<UserListDto> expected = List.of(userListDto);
        List<UserEntity> userEntityList = List.of(userEntity);

        List<UserListDto> actual = userMapper.toListDto(userEntityList);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expected, actual);
    }

    @DisplayName("JUnit test for map userSaveUpdateDto to userEntity")
    @Test
    void toEntityFromUserSaveUpdateDtoTest() {
        UserEntity expected = new UserEntity();
        expected.setName(userSaveUpdateDto.getName());
        expected.setEmail(userSaveUpdateDto.getEmail());

        UserEntity actual = userMapper.toEntity(userSaveUpdateDto);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
