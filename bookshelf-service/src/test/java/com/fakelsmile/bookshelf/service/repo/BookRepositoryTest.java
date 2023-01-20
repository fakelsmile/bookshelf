package com.fakelsmile.bookshelf.service.repo;

import com.fakelsmile.bookshelf.service.models.entity.BookEntity;
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
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("JUnit test for findByIdEmptyResult method")
    @Test
    public void findByIdEmptyResultTest() {
        Optional<BookEntity> found = bookRepository.findById(99L);
        assertFalse(found.isPresent());
    }

    @DisplayName("JUnit test for findById method")
    @Test
    @Sql(scripts={"classpath:sql/createUser.sql", "classpath:sql/createBook.sql"})
    public void findByIdTest() {
        Optional<BookEntity> found = bookRepository.findById(1L);
        BookEntity actual = found.get();
        assertEquals(1L, actual.getId());
    }

    @DisplayName("JUnit test for save method")
    @Test
    @Sql(scripts={"classpath:sql/createUser.sql"})
    public void saveTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Zaph");

        BookEntity expected = BookEntity.builder()
                .id(1L)
                .name("Ramesh")
                .description("Fadatare")
                .fullText("ramesh@gmail.com")
                .views(0)
                .author(userEntity)
                .build();
        expected = bookRepository.save(expected);

        Optional<BookEntity> found = bookRepository.findById(expected.getId());
        BookEntity actual = found.get();
        assertEquals(expected.getId(), actual.getId());
    }

    @DisplayName("JUnit test for findAll method")
    @Test
    @Sql(scripts={"classpath:sql/createUser.sql", "classpath:sql/createBookList.sql"})
    public void findAllTest() {
        List<BookEntity> actual = bookRepository.findAll();
        assertEquals(3, actual.size());
    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    @Sql(scripts={"classpath:sql/createUser.sql", "classpath:sql/createBookList.sql"})
    public void deleteByIdTest() {
        bookRepository.deleteById(1L);
        List<BookEntity> actual = bookRepository.findAll();
        assertEquals(2, actual.size());
    }
}
