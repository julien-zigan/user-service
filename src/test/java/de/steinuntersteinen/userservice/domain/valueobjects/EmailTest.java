package de.steinuntersteinen.userservice.domain.valueobjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {
    @Test
    void emailShouldImplementValueEquality() {
        String emailAddress = "test@email.com";
        assertThat(new Email(emailAddress)).isEqualTo(new Email(emailAddress));
    }

    @Test
    void creatingEmailWithNullShouldFail() {
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Email cannot be null");
    }

    @Test
    void creatingEmailWithBlankShouldFail() {
        assertThatThrownBy(() -> new Email(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email cannot be empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "   ",
            "no-at-symbol.com",
            "missing-domain@",
            "@missing-local",
            "noTLDbar",
            "invalidDomainStart@.com",
            "invalidDomainEnd@com.",
            "DoubleAt@@example.com",
            "white space@example.com",
            "doubleDot@example..com",
            "domainStartsWithHyphen@-example.com",
            "domainEndsWithHyphen@example-.com",
            "underscoreInDomain@exam_ple.com",
    })
    void invalidEmailShouldFail(String invalidEmail) {
        assertThatThrownBy(() -> new Email(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validEmailShouldPassAndBeLowercased() {
        Email email = new Email("UPPERCASE@Example.COM");
        assertThat(email.value()).isEqualTo("uppercase@example.com");
    }

    @Test
    void whitespaceShouldBeTrimmed() {
        Email email = new Email("  test@example.com  ");
        assertThat(email.value()).isEqualTo("test@example.com");
    }


    @Test
    void userIdShouldExposeCorrectValue() {
        String emailAddress = "test@email.com";
        Email email = new Email(emailAddress);
        assertThat(email.value()).isEqualTo(emailAddress);
    }
}