package de.steinuntersteinen.userservice.domain.valueobjects;

import java.time.Instant;
import java.util.Objects;

public record UpdatedAt(Instant value) {
    public UpdatedAt {
        Objects.requireNonNull(value, "UpdatedAt cannot be null");

        if (value.isAfter(Instant.now())) {
            throw new IllegalArgumentException("UpdatedAt cannot be in the future");
        }
    }

    public UpdatedAt update() {
        return new UpdatedAt(Instant.now());
    }

    public Instant toInstant() {
        return value;
    }
}
