package ru.aston.hometask.validator.valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.api.IUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EmailValidatorTest {
    private IUserValidator validator;
    private ValidationContext validationContext;

    @BeforeEach
    void init() {
        validationContext = new ValidationContext();
        validator = new EmailValidator();
    }

    @Test
    void when_emailNull_then_addError() {
        UserCreateDto user = UserCreateDto.builder().build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Не указан электронный адрес");
    }

    @Test
    void when_emailEmpty_then_addError() {
        UserCreateDto user = UserCreateDto.builder().setEmail("  ").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Не указан электронный адрес");
    }

    @Test
    void when_emailWithoutAt_then_addError() {
        UserCreateDto user = UserCreateDto.builder().setEmail("examplemail.com").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Указан неверный формат электронного адреса");
    }

    @Test
    void when_emailWithoutDomain_then_addError() {
        UserCreateDto user = UserCreateDto.builder().setEmail("example@mail").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Указан неверный формат электронного адреса");
    }

    @Test
    void when_incorrectAddress_then_addError() {
        UserCreateDto user = UserCreateDto.builder().setEmail("примерexample@mail").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Указан неверный формат электронного адреса");
    }

    @Test
    void when_validEmail_then_noError() {
        UserCreateDto user = UserCreateDto.builder().setEmail("example@mail.org").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).isEmpty();
    }
}