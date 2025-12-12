package de.steinuntersteinen.userservice.domain.events;

import de.steinuntersteinen.userservice.domain.valueobjects.Email;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;

import java.time.Instant;

public record UserCreated(
        UserId userId,
        Email email,
        Instant occurredAt
) implements DomainEvent {}
