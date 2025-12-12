package de.steinuntersteinen.userservice.domain.valueobjects;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreatedAtTest {
    @Test
    void nullTimestampShouldFail() {
        assertThatThrownBy(() -> new CreatedAt(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void instantInFutureShouldThrow() {
        Instant future = Instant.now().plus(Duration.ofDays(1));
        assertThatThrownBy(() -> new CreatedAt(future))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void toInstantShouldReturnCorrectValue() {
        Instant expectedValue = Instant.now();
        CreatedAt createdAt = new CreatedAt(expectedValue);
        assertThat(createdAt.toInstant()).isEqualTo(expectedValue);
    }

    @Test
    void validTimestampShouldPass() {
        Instant now = Instant.now();
        CreatedAt createdAt = new CreatedAt(now);
        assertThat(createdAt.value()).isEqualTo(now);
    }
}