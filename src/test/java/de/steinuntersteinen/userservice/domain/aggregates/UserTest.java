package de.steinuntersteinen.userservice.domain.aggregates;

import de.steinuntersteinen.userservice.domain.events.DomainEvent;
import de.steinuntersteinen.userservice.domain.events.UserCreated;
import de.steinuntersteinen.userservice.domain.events.UserEmailChanged;
import de.steinuntersteinen.userservice.domain.valueobjects.Email;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void creatingUserShouldSetUserIdEmailAndDisplayName() {
        UserId id = new UserId(UUID.randomUUID());
        Email email = new Email("user@example.com");

        User user = User.create(id, email);

        assertThat(user.getUserId()).isEqualTo(id);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    void creatingUserShouldInitializeTimestamps() {
        User user = User.create(
                new UserId(UUID.randomUUID()),
                new Email("user@example.com")
        );

        assertThat(user.getCreatedAt().value()).isNotNull();
        assertThat(user.getUpdatedAt().value()).isNotNull();
        assertThat(user.getUpdatedAt().value())
                .isEqualTo(user.getCreatedAt().value()); // createdAt == updatedAt during creation
    }

    @Test
    void creatingUserShouldProduceUserCreatedEvent() {
        UserId id = new UserId(UUID.randomUUID());
        Email email = new Email("user@example.com");

        User user = User.create(id, email);
        List<DomainEvent> events = user.pullDomainEvents();

        assertThat(events).hasSize(1);
        assertThat(events.get(0)).isInstanceOf(UserCreated.class);

        UserCreated event = (UserCreated) events.get(0);

        assertThat(event.userId()).isEqualTo(id);
        assertThat(event.email()).isEqualTo(email);
        assertThat(event.occurredAt()).isEqualTo(user.getCreatedAt().value());
    }

    @Test
    void changingEmailShouldUpdateEmailAndProduceEmailChangedEvent() {
        UserId id = new UserId(UUID.randomUUID());
        Email oldEmail = new Email("old@email.com");

        User user = User.create(id, oldEmail);
        user.pullDomainEvents();

        Email newEmail = new Email("new@email.com");

        user.changeEmail(newEmail);
        List<DomainEvent> events = user.pullDomainEvents();

        assertThat(user.getEmail()).isEqualTo(newEmail);

        assertThat(events).hasSize(1);
        assertThat(events.get(0)).isInstanceOf(UserEmailChanged.class);

        UserEmailChanged event = (UserEmailChanged) events.get(0);

        assertThat(event.userId()).isEqualTo(id);
        assertThat(event.oldEmail()).isEqualTo(oldEmail);
        assertThat(event.newEmail()).isEqualTo(newEmail);
    }

    @Test
    void updatingEmailWithoutNewValueShouldNotEmitEvent() {
        UserId id = new UserId(UUID.randomUUID());
        Email sameEmail = new Email("same@email.com");

        User user = User.create(id, sameEmail);
        user.pullDomainEvents();

        user.changeEmail(sameEmail);
        List<DomainEvent> events = user.pullDomainEvents();

        assertThat(events).hasSize(0);
    }
}