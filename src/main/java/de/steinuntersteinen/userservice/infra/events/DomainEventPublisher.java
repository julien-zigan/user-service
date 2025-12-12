package de.steinuntersteinen.userservice.infra.events;

import de.steinuntersteinen.userservice.domain.events.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {
    void publish(List<DomainEvent> events);
}
