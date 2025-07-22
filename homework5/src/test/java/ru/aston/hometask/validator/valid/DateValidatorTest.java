package ru.aston.hometask.validator.valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.api.IUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DateValidatorTest {
    private IUserValidator validator;
    private ValidationContext validationContext;

    @BeforeEach
    void init() {
        validationContext = new ValidationContext();
        validator = new DateValidator();
    }

    @Test
    void when_birthDateNull_then_getError() {
        UserCreateDto user = UserCreateDto.builder().build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Не введена дата");
    }

    @Test
    void when_birthDateEmpty_then_getError() {
        UserCreateDto user = UserCreateDto.builder()
                .setBirthdate("")
                .build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Не введена дата");
    }

    @Test
    void when_birthDateInvalidFormat_then_getError() {
        UserCreateDto user = UserCreateDto.builder()
                .setBirthdate("01-01-2000")
                .build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("введенная дата не соответствует формату");
    }

    @Test
    void when_birthDateValidFormat_then_noError() {
        UserCreateDto user = UserCreateDto.builder()
                .setBirthdate("2020-01-01")
                .build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).isEmpty();
    }
}