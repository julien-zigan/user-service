package de.steinuntersteinen.userservice.domain.aggregates;

import de.steinuntersteinen.userservice.domain.events.DomainEvent;
import de.steinuntersteinen.userservice.domain.events.UserCreated;
import de.steinuntersteinen.userservice.domain.events.UserEmailChanged;
import de.steinuntersteinen.userservice.domain.valueobjects.CreatedAt;
import de.steinuntersteinen.userservice.domain.valueobjects.Email;
import de.steinuntersteinen.userservice.domain.valueobjects.UpdatedAt;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;

import java.time.Instant;
import java.util.Objects;

public class User extends AggregateRoot {
    private final UserId userId;
    private final CreatedAt createdAt;
    private Email email;
    private UpdatedAt updatedAt;

    private User(UserId userId, CreatedAt createdAt, Email email, UpdatedAt updatedAt) {
        this.userId = Objects.requireNonNull(userId);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.email = Objects.requireNonNull(email);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static User create(UserId userId, Email email) {
        Instant now = Instant.now();
        User user = new User(
                userId,
                new CreatedAt(now),
                email,
                new UpdatedAt(now)
        );
        user.addDomainEvent(new UserCreated(userId, email, now));
        return user;
    }

    public static User rehydrate(UserId userId, CreatedAt createdAt, Email email, UpdatedAt updatedAt) {
        return new User(userId, createdAt, email, updatedAt);
    }

    public UserId getUserId() { return userId; }
    public Email getEmail() { return email; }
    public CreatedAt getCreatedAt() { return createdAt; }
    public UpdatedAt getUpdatedAt() { return updatedAt; }

    public void changeEmail(Email newEmail) {
        Objects.requireNonNull(newEmail, "Email cannot be null");
        if (!this.email.equals(newEmail)) {
            updateField(
                    () -> this.email = newEmail,
                    new UserEmailChanged(userId, this.email, newEmail, Instant.now())
            );
        }
    }

    private void updateField(Runnable update, DomainEvent event) {
        update.run();
        this.updatedAt = new UpdatedAt(Instant.now());
        addDomainEvent(event);
    }
}
