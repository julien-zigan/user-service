package de.steinuntersteinen.userservice.application.services;

import de.steinuntersteinen.userservice.domain.valueobjects.UserId;

public interface UserService {
    UserId registerUser(String email);

    void changeUserEmail(UserId id, String newEmail);
}
