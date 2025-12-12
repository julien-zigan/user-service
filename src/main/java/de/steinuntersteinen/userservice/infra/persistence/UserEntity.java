package de.steinuntersteinen.userservice.infra.persistence;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uK_users_email", columnNames = "email")
        }
)
public class UserEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false)
    UUID id;

    @Column(name = "email", nullable = false)
    String email;
}
