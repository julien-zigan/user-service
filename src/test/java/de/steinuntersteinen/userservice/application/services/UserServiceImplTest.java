package de.steinuntersteinen.userservice.application.services;

import de.steinuntersteinen.userservice.domain.aggregates.User;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;
import de.steinuntersteinen.userservice.domain.valueobjects.Email;
import de.steinuntersteinen.userservice.domain.repositories.UserRepository;
import de.steinuntersteinen.userservice.infra.events.DomainEventPublisher;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void registerUserShouldCreateUserAndPublishEvents() {
        UserRepository repository = mock(UserRepository.class);
        DomainEventPublisher eventPublisher = mock(DomainEventPublisher.class);
        UserService service = new UserServiceImpl(eventPublisher, repository);
        String email = "user@example.com";

        UserId userId = service.registerUser(email);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getUserId()).isEqualTo(userId);
        assertThat(savedUser.getEmail().value()).isEqualTo(email);

        verify(eventPublisher).publish(savedUser.pullDomainEvents());
    }
}
