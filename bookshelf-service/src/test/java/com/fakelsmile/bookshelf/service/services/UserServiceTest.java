package com.fakelsmile.bookshelf.service.services;

import com.fakelsmile.bookshelf.service.errors.UserNotFoundException;
import com.fakelsmile.bookshelf.service.models.UserRole;
import com.fakelsmile.bookshelf.service.models.entity.UserEntity;
import com.fakelsmile.bookshelf.service.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserEntity user;

    private UserEntity savedUser;

    @BeforeEach
    public void setup() {
        user = UserEntity.builder()
                .name("Zaph")
                .email("Zaph@gmail")
                .password("qwerty123")
                .role(UserRole.USER)
                .build();

        savedUser = UserEntity.builder()
                .id(1L)
                .name("Zaph")
                .email("Zaph@gmail")
                .password("qwerty123")
                .role(UserRole.USER)
                .build();
    }

    @AfterEach
    public void turnDown() {
        verifyNoMoreInteractions(userRepository);
    }

    @DisplayName("JUnit test for saveUser method")
    @Test
    public void saveUserTest() {
        when(userRepository.save(user)).thenReturn(savedUser);
        UserEntity actual = userService.saveUser(user);

        assertEquals(savedUser, actual);
        verify(userRepository).save(user);
    }

    @DisplayName("JUnit test for getAllUsers method")
    @Test
    public void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(List.of(user, savedUser));
        List<UserEntity> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(user, users.get(0));
        assertEquals(savedUser, users.get(1));
        verify(userRepository).findAll();
    }

    @DisplayName("JUnit test for getUser method")
    @Test
    public void getUserTest() throws UserNotFoundException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(savedUser));
        UserEntity actual = userService.getUser(savedUser.getId());

        assertEquals(savedUser, actual);
        verify(userRepository).findById(1L);
    }

    @DisplayName("JUnit test for updateUser method")
    @Test
    public void updateUserTest() throws UserNotFoundException {
        UserEntity userForUpdate = UserEntity.builder()
                .id(1L)
                .name("Zaph")
                .email("Zaph@gmail")
                .password("qwerty123")
                .role(UserRole.USER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(userForUpdate));
        when(userRepository.save(savedUser)).thenReturn(savedUser);

        UserEntity actual = userService.updateUser(savedUser.getId(), savedUser);

        assertEquals(savedUser, actual);
        verify(userRepository).findById(1L);
        verify(userRepository).save(savedUser);
    }

    @DisplayName("JUnit test for deleteUser method")
    @Test
    public void deleteUserTest() {
        long userId = 1L;
        willDoNothing().given(userRepository).deleteById(userId);
        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }

    @DisplayName("JUnit test for updateUser method with error")
    @Test
    public void updateUserWithErrorTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(savedUser.getId(), savedUser));

        verify(userRepository).findById(1L);
    }

    @DisplayName("JUnit test for getUser method with error")
    @Test
    public void getUserWithErrorTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUser(savedUser.getId()));

        verify(userRepository).findById(1L);
    }
}
