package ru.aston.hometask.validator.valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.api.IUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordValidatorTest {
    private IUserValidator validator;
    private ValidationContext validationContext;

    @BeforeEach
    void init() {
        validationContext = new ValidationContext();
        validator = new PasswordValidator();
    }

    @Test
    void when_passwordNull_then_addError() {
        UserCreateDto user = UserCreateDto.builder().build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Не введен пароль или состоит из пробельных символов");
    }

    @Test
    void when_passwordEmpty_then_addError() {
        UserCreateDto user = UserCreateDto.builder().setPassword("  ").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Не введен пароль или состоит из пробельных символов");
    }

    @Test
    void when_passwordTooShort_then_addError() {
        UserCreateDto user = UserCreateDto.builder().setPassword("123").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Длина пароля должна быть не менее 4 символов");
    }

    @Test
    void when_passwordValid_then_noError() {
        UserCreateDto user = UserCreateDto.builder().setPassword("54321").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).isEmpty();
    }
}