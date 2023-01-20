package com.fakelsmile.bookshelf.service.repo;

import com.fakelsmile.bookshelf.service.models.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for BookEntity entities.
 * Provides basic CRUD operations inherited from JpaRepository.
 */
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
