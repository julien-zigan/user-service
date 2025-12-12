package de.steinuntersteinen.userservice.domain.repositories;

import de.steinuntersteinen.userservice.domain.aggregates.User;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(UserId userId);

    boolean existsById(UserId userId);
}
