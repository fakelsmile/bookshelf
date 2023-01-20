package com.fakelsmile.bookshelf.service.models.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

/**
 * Base entity.
 */
@NoArgsConstructor
@SuperBuilder
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    @CreatedDate
    @Column(name = "create_at")
    private Instant createAt;
    @LastModifiedDate
    @Column(name = "update_at")
    private Instant updateAt;
}
