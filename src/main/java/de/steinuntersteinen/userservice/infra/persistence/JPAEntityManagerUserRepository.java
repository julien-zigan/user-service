package de.steinuntersteinen.userservice.infra.persistence;

import de.steinuntersteinen.userservice.domain.aggregates.User;
import de.steinuntersteinen.userservice.domain.repositories.UserRepository;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public class JPAEntityManagerUserRepository implements UserRepository {
    private final UserMapper mapper;
    private final EntityManager entityManager;

    @Autowired
    public JPAEntityManagerUserRepository(UserMapper userMapper, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.mapper = userMapper;
    }

    @Override
    @Transactional
    public void save(User user) {
        UserEntity userEntity = mapper.toEntity(user);
        entityManager.persist(userEntity);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        UserEntity userEntity= entityManager.find(UserEntity.class, userId.value());
        if (Objects.nonNull(userEntity)) {
            User user = mapper.toDomain(userEntity);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(UserId userId) {
        return findById(userId).isPresent();
    }
}
