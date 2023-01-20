package com.fakelsmile.bookshelf.service.repo;

import com.fakelsmile.bookshelf.service.models.UserRole;
import com.fakelsmile.bookshelf.service.models.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQLDB)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("JUnit test for findByIdEmptyResult method")
    @Test
    public void findByIdEmptyResultTest() {
        Optional<UserEntity> found = userRepository.findById(99L);
        assertFalse(found.isPresent());
    }

    @DisplayName("JUnit test for findById method")
    @Test
    @Sql(scripts={"classpath:sql/createUser.sql"})
    public void findByIdTest() {
        Optional<UserEntity> found = userRepository.findById(1L);
        UserEntity actual = found.get();
        assertEquals(1L, actual.getId());
    }

    @DisplayName("JUnit test for save method")
    @Test
    @Sql(scripts={"classpath:sql/createUser.sql"})
    public void saveTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Zaph");
        userEntity.setEmail("Zaph@gmail");

        UserEntity expected = UserEntity.builder()
                .id(1L)
                .name("Zaph")
                .email("Zaph@gmail")
                .password("qwerty123")
                .role(UserRole.USER)
                .build();
        expected = userRepository.save(expected);

        Optional<UserEntity> found = userRepository.findById(expected.getId());
        UserEntity actual = found.get();
        assertEquals(expected.getId(), actual.getId());
    }

    @DisplayName("JUnit test for findAll method")
    @Test
    @Sql(scripts={"classpath:sql/createUserList.sql"})
    public void findAllTest() {
        List<UserEntity> actual = userRepository.findAll();
        assertEquals(3, actual.size());
    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    @Sql(scripts={"classpath:sql/createUserList.sql"})
    public void deleteByIdTest() {
        userRepository.deleteById(1L);
        List<UserEntity> actual = userRepository.findAll();
        assertEquals(2, actual.size());
    }
}
