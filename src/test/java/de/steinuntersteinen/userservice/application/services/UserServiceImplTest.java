package de.steinuntersteinen.userservice.application.services;

import de.steinuntersteinen.userservice.domain.aggregates.User;
import de.steinuntersteinen.userservice.domain.events.DomainEvent;
import de.steinuntersteinen.userservice.domain.events.UserCreated;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;
import de.steinuntersteinen.userservice.domain.repositories.UserRepository;
import de.steinuntersteinen.userservice.infra.events.DomainEventPublisher;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void registerUserShouldCreateUser() {
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
    }

    @Test
    void registerUser_ShouldPublishEvent() {
        UserRepository repository = mock(UserRepository.class);
        DomainEventPublisher eventPublisher = mock(DomainEventPublisher.class);
        UserService service = new UserServiceImpl(eventPublisher, repository);
        ArgumentCaptor<List<DomainEvent>> captor = ArgumentCaptor.forClass(List.class);
        String testEmail = "test@email.com";

        service.registerUser(testEmail);

        verify(eventPublisher).publish(captor.capture());

        List<DomainEvent> events = captor.getValue();

        assertThat(events).hasSize(1);
        assertThat(events.get(0)).isInstanceOf(UserCreated.class);

        UserCreated event = (UserCreated) events.get(0);
        assertThat(event.email().value()).isEqualTo(testEmail);
    }
}
