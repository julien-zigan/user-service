package de.steinuntersteinen.userservice.domain.events;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredAt();
}
