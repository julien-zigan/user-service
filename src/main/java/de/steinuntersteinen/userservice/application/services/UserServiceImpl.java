package de.steinuntersteinen.userservice.application.services;

import de.steinuntersteinen.userservice.domain.aggregates.User;
import de.steinuntersteinen.userservice.domain.repositories.UserRepository;
import de.steinuntersteinen.userservice.domain.valueobjects.Email;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;
import de.steinuntersteinen.userservice.infra.events.DomainEventPublisher;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class UserServiceImpl implements UserService {
    private final DomainEventPublisher eventPublisher;
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(DomainEventPublisher eventPublisher, UserRepository repository) {
        this.eventPublisher = eventPublisher;
        this.repository = repository;
    }

    @Transactional
    @Override
    public UserId registerUser(String email) {
        User user = User.create(
                new UserId(UUID.randomUUID()),
                new Email(email)
        );
        repository.save(user);
        eventPublisher.publish(user.pullDomainEvents());
        return user.getUserId();
    }

    @Override
    public void changeUserEmail(UserId id, String newEmail) {

    }
}
