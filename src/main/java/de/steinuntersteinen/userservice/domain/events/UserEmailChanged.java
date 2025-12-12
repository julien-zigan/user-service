package de.steinuntersteinen.userservice.domain.events;

import de.steinuntersteinen.userservice.domain.valueobjects.Email;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;

import java.time.Instant;

public record UserEmailChanged(
        UserId userId,
        Email oldEmail,
        Email newEmail,
        Instant occurredAt
) implements DomainEvent {}
