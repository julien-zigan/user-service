package de.steinuntersteinen.userservice.domain.events;

import de.steinuntersteinen.userservice.domain.valueobjects.Email;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserEmailChangedTest {
    @Test
    void eventShouldStoreAllValuesCorrectly() {
        UserId userId = new UserId(UUID.randomUUID());
        Email oldEmail = new Email("old@example.com");
        Email newEmail = new Email("new@example.com");
        Instant now = Instant.now();

        UserEmailChanged event = new UserEmailChanged(
                userId,
                oldEmail,
                newEmail,
                now
        );

        assertThat(event.userId()).isEqualTo(userId);
        assertThat(event.oldEmail()).isEqualTo(oldEmail);
        assertThat(event.newEmail()).isEqualTo(newEmail);
        assertThat(event.occurredAt()).isEqualTo(now);
    }
}