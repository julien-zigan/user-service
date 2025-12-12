package de.steinuntersteinen.userservice.infra.persistence;

import de.steinuntersteinen.userservice.domain.aggregates.User;
import de.steinuntersteinen.userservice.domain.valueobjects.CreatedAt;
import de.steinuntersteinen.userservice.domain.valueobjects.Email;
import de.steinuntersteinen.userservice.domain.valueobjects.UpdatedAt;
import de.steinuntersteinen.userservice.domain.valueobjects.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(User user)
    {
        return new UserEntity(
                user.getUserId().value(),
                user.getEmail().value(),
                user.getCreatedAt().value(),
                user.getUpdatedAt().value()
        );
    }

    public User toDomain(UserEntity entity) {
        return User.rehydrate(
                new UserId(entity.getId()),
                new CreatedAt(entity.getCreatedAt()),
                new Email(entity.getEmail()),
                new UpdatedAt(entity.getUpdatedAt())
        );
    }
}
