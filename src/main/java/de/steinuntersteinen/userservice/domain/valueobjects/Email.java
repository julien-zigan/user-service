package de.steinuntersteinen.userservice.domain.valueobjects;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^(?=.{1,254}$)(?=.{1,64}@)[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+" +
                    "(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
                    "(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+" +
                    "[A-Za-z]{2,}$"
    );

    public Email {
        Objects.requireNonNull(value, "Email cannot be null");

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (!EMAIL_REGEX.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + trimmed);
        }

        value = trimmed.toLowerCase(Locale.ROOT);
    }
}
