package com.fakelsmile.bookshelf.service.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * Entity for book.
 */
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper=true, exclude="author")
@Data
public class BookEntity extends BaseEntity {
    @Column(nullable=false)
    private String description;
    @Column(name = "full_text")
    private String fullText;
    private Integer views;
    @ManyToOne
    @JoinColumn(name="author_id", nullable=false, referencedColumnName = "id")
    private UserEntity author;
}
