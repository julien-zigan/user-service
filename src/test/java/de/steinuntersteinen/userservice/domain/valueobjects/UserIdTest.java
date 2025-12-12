package de.steinuntersteinen.userservice.domain.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserIdTest {
    @Test
    void userIdShouldImplementValueEquality() {
        UUID uuid = UUID.randomUUID();
        assertThat(new UserId(uuid)).isEqualTo(new UserId(uuid));
    }

    @Test
    void creatingUserWithNullShouldFail() {
        assertThatThrownBy(() -> new UserId(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("UserId cannot be null");
    }

    @Test
    void userIdShouldExposeCorrectValue() {
        UUID expectedUuid = UUID.randomUUID();
        UserId userId = new UserId(expectedUuid);
        assertThat(userId.value()).isEqualTo(expectedUuid);
    }
}