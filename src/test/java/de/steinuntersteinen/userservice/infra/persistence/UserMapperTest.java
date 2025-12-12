package de.steinuntersteinen.userservice.infra.persistence;

import de.steinuntersteinen.userservice.domain.aggregates.User;
import de.steinuntersteinen.userservice.domain.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper mapper;

    @Test
    void toEntity_shouldMapDomainUserToEntity() {
        UserId id = new UserId(UUID.randomUUID());
        Email email = new Email("user@example.com");
        CreatedAt createdAt = new CreatedAt(Instant.now());
        UpdatedAt updatedAt = new UpdatedAt(createdAt.value());

        User user = User.rehydrate(id, createdAt, email, updatedAt);

        UserEntity entity = mapper.toEntity(user);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(id.value());
        assertThat(entity.getEmail()).isEqualTo(email.value());
        assertThat(entity.getCreatedAt()).isEqualTo(createdAt.value());
        assertThat(entity.getUpdatedAt()).isEqualTo(updatedAt.value());
    }

    @Test
    void toDomain_shouldMapEntityToDomainUser() {
        UUID id = UUID.randomUUID();
        String email = "user@example.com";
        Instant now = Instant.now();

        UserEntity entity = new UserEntity(id, email, now, now);

        User user = mapper.toDomain(entity);

        assertThat(user).isNotNull();
        assertThat(user.getUserId().value()).isEqualTo(id);
        assertThat(user.getEmail().value()).isEqualTo(email);
        assertThat(user.getCreatedAt().value()).isEqualTo(now);
        assertThat(user.getUpdatedAt().value()).isEqualTo(now);
    }
}
