package com.fakelsmile.bookshelf.service.controllers;

import com.fakelsmile.bookshelf.service.TestUtil;
import com.fakelsmile.bookshelf.service.mappers.UserMapper;
import com.fakelsmile.bookshelf.service.models.UserRole;
import com.fakelsmile.bookshelf.service.models.dto.UserDto;
import com.fakelsmile.bookshelf.service.models.entity.UserEntity;
import com.fakelsmile.bookshelf.service.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.fakelsmile.bookshelf.service.TestUtil.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    private UserEntity userEntity;
    private UserEntity savedUserEntity;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userEntity = UserEntity.builder()
                .id(3L)
                .name("Ramesh")
                .email("ramesh@gmail.com")
                .password("Fadatare")
                .role(UserRole.USER)
                .build();

        userDto = new UserDto();
        userDto.setName("Ramesh");
        userDto.setEmail("ramesh@gmail.com");
        userDto.setPassword("Fadatare");
        userDto.setRole(UserRole.USER);

        savedUserEntity = UserEntity.builder()
                .id(3L)
                .name("Ramesh")
                .email("ramesh@gmail.com")
                .password("Fadatare")
                .role(UserRole.USER)
                .build();

    }

    @AfterEach
    public void turnDown() {
        verifyNoMoreInteractions(userMapper, userService);
    }

    @DisplayName("JUnit test for getAllUsers method")
    @Test
    public void getAllUsersTest() throws Exception {
        List<UserEntity> userEntityList = List.of(userEntity);
        List<UserDto> expected = List.of(userDto);

        when(userService.getAllUsers()).thenReturn(userEntityList);
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        List<UserDto> actual = TestUtil.objectMapper.readValue(jsonResult, new TypeReference<List<UserDto>>() {});

        assertEquals(expected, actual);
        verify(userService).getAllUsers();
        verify(userMapper).toDto(userEntity);

    }

    @DisplayName("JUnit test for addUser method")
    @Test
    public void addUserTest() throws Exception {
        UserDto expected = new UserDto();
        expected.setId(1L);
        expected.setName("Ramesh");
        expected.setEmail("ramesh@gmail.com");
        expected.setPassword("Fadatare");
        expected.setRole(UserRole.USER);

        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(userService.saveUser(userEntity)).thenReturn(savedUserEntity);
        when(userMapper.toDto(savedUserEntity)).thenReturn(expected);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        UserDto actual = TestUtil.objectMapper.readValue(jsonResult, UserDto.class);

        assertEquals(expected, actual);
        verify(userMapper).toEntity(userDto);
        verify(userService).saveUser(userEntity);
        verify(userMapper).toDto(savedUserEntity);
    }

    @DisplayName("JUnit test for getUser method")
    @Test
    public void getUserTest() throws Exception {
        UserDto expected = new UserDto();
        expected.setId(1L);
        expected.setName("Ramesh");
        expected.setEmail("ramesh@gmail.com");
        expected.setPassword("Fadatare");
        expected.setRole(UserRole.USER);

        when(userService.getUser(userEntity.getId())).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(expected);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/" + userEntity.getId())
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        UserDto actual = TestUtil.objectMapper.readValue(jsonResult, UserDto.class);

        assertEquals(expected, actual);
        verify(userService).getUser(userEntity.getId());
        verify(userMapper).toDto(userEntity);

    }

    @DisplayName("JUnit test for updateUser method")
    @Test
    public void updateUserTest() throws Exception {
        UserDto expected = new UserDto();
        expected.setId(1L);
        expected.setName("Ramesh");
        expected.setEmail("ramesh@gmail.com");
        expected.setPassword("Fadatare");
        expected.setRole(UserRole.USER);

        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(userService.updateUser(userEntity.getId(), userEntity)).thenReturn(savedUserEntity);
        when(userMapper.toDto(savedUserEntity)).thenReturn(expected);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users/" + userEntity.getId())
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        UserDto actual = TestUtil.objectMapper.readValue(jsonResult, UserDto.class);

        assertEquals(expected, actual);
        verify(userMapper).toEntity(userDto);
        verify(userService).updateUser(userEntity.getId(), userEntity);
        verify(userMapper).toDto(savedUserEntity);
    }

    @DisplayName("JUnit test for deleteUser method")
    @Test
    public void deleteUserTest() throws Exception {
        long userId = 3L;

        doNothing().when(userService).deleteUser(userId);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).deleteUser(userId);

    }
}