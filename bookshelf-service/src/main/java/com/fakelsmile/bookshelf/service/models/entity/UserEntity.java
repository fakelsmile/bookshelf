package com.fakelsmile.bookshelf.service.models.entity;

import com.fakelsmile.bookshelf.service.models.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

/**
 * Entity for user.
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper=true, exclude="books")
@ToString(exclude="books")
@Data
public class UserEntity extends BaseEntity {
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @OneToMany(mappedBy="author")
    private List<BookEntity> books;
}
