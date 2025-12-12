package de.steinuntersteinen.userservice.domain.events;

import de.steinuntersteinen.userservice.domain.valueobjects.Email;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreatedTest {
    @Test
    void userCreatedEventShouldHoldValues() {
        Instant now = Instant.now();
        UserCreated event = new UserCreated(
                new UserId(UUID.randomUUID()),
                new Email("test@example.com"),
                now
        );

        assertThat(event.occurredAt()).isEqualTo(now);
        assertThat(event.email().value()).isEqualTo("test@example.com");
    }

}