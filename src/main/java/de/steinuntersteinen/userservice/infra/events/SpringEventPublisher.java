package de.steinuntersteinen.userservice.infra.events;

import de.steinuntersteinen.userservice.domain.events.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher publisher;

    @Autowired
    public SpringEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(List<DomainEvent> events) {
        for (DomainEvent event : events) {
            publisher.publishEvent(event);
        }
    }
}
