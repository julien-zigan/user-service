package de.steinuntersteinen.userservice.domain.repositories;

import de.steinuntersteinen.userservice.domain.aggregates.User;
import de.steinuntersteinen.userservice.domain.valueobjects.*;
import de.steinuntersteinen.userservice.infra.persistence.JPAEntityManagerUserRepository;
import de.steinuntersteinen.userservice.infra.persistence.UserEntity;
import de.steinuntersteinen.userservice.infra.persistence.UserMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@DataJpaTest
@Import({JPAEntityManagerUserRepository.class, UserMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserMapper mapper;

    @Test
    void save_shouldPersistUserToDatabase() {
        UserId id = new UserId(UUID.randomUUID());
        Email email = new Email("test@email.com");
        CreatedAt createdAt = new CreatedAt(Instant.now());
        UpdatedAt updatedAt = new UpdatedAt(Instant.now());
        User user = User.rehydrate(id, createdAt, email, updatedAt);

        repository.save(user);
        entityManager.flush();

        UserEntity found = entityManager.find(UserEntity.class, id.value());
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("test@email.com");
    }

    @Test
    void findById_shouldReturnUserIfExists() {
        UserId id = new UserId(UUID.randomUUID());
        Email email = new Email("test@email.com");
        CreatedAt createdAt = new CreatedAt(Instant.now());
        UpdatedAt updatedAt = new UpdatedAt(createdAt.value());
        User domainUser = User.rehydrate(id, createdAt, email, updatedAt);

        UserEntity entity = mapper.toEntity(domainUser);
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.clear();

        Optional<User> result = repository.findById(id);

        assertThat(result).isPresent();
        User user = result.get();

        assertThat(user.getUserId()).isEqualTo(id);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(
                user.getCreatedAt().value())
                .isCloseTo(createdAt.value(), within(5, ChronoUnit.MILLIS)
                );
        assertThat(
                user.getUpdatedAt().value())
                .isCloseTo(updatedAt.value(), within(5, ChronoUnit.MILLIS));
    }

    @Test
    void findById_shouldReturnEmptyOptionalIfNotExists() {
        UserId id = new UserId(UUID.randomUUID());

        Optional<User> result = repository.findById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void existsById_shouldReturnTrueIfExists() {
        UserId id = new UserId(UUID.randomUUID());
        Email email = new Email("test@email.com");
        CreatedAt createdAt = new CreatedAt(Instant.now());
        UpdatedAt updatedAt = new UpdatedAt(createdAt.value());
        User domainUser = User.rehydrate(id, createdAt, email, updatedAt);
        UserEntity entity = mapper.toEntity(domainUser);

        entityManager.persist(entity);
        entityManager.flush();
        entityManager.clear();

        assertThat(repository.existsById(id)).isEqualTo(true);
    }

    @Test
    void existsById_shouldReturnFalseIfNotExists() {
        UserId id = new UserId(UUID.randomUUID());

        assertThat(repository.existsById(id)).isEqualTo(false);
    }
}
