package de.steinuntersteinen.userservice.domain.valueobjects;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UpdatedAtTest {
    @Test
    void updatedAtShouldImplementValueEquality() {
        Instant now = Instant.now();
        assertThat(new UpdatedAt(now)).isEqualTo(new UpdatedAt(now));
    }

    @Test
    void updatedAtShouldExposeCorrectValue() {
        Instant expectedValue = Instant.now();
        UpdatedAt updatedAt = new UpdatedAt(expectedValue);
        assertThat(updatedAt.value()).isEqualTo(expectedValue);
    }

    @Test
    void updateShouldHoldNowAsValue() {
        Instant yesterday = Instant.now().minus(Duration.ofDays(1));
        UpdatedAt updatedAt = new UpdatedAt(yesterday);
        assertThat(updatedAt.update().value()).isBetween(
                Instant.now().minus(Duration.ofMillis(500)),
                Instant.now()
        );

    }

    @Test
    void toInstantShouldReturnCorrectValue() {
        Instant expectedValue = Instant.now();
        UpdatedAt updatedAt = new UpdatedAt(expectedValue);
        assertThat(updatedAt.toInstant()).isEqualTo(expectedValue);
    }

    @Test
    void nullTimestampShouldFail() {
        assertThatThrownBy(() -> new UpdatedAt(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void validTimestampShouldPass() {
        Instant now = Instant.now();
        UpdatedAt updatedAt = new UpdatedAt(now);
        assertThat(updatedAt.value()).isEqualTo(now);
    }

    @Test
    void instantInFutureShouldThrow() {
        Instant future = Instant.now().plus(Duration.ofDays(1));
        assertThatThrownBy(() -> new UpdatedAt(future))
                .isInstanceOf(IllegalArgumentException.class);
    }
}