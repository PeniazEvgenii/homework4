package ru.aston.hometask.validator.valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.api.IUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FirstnameValidatorTest {
    private IUserValidator validator;
    private ValidationContext validationContext;

    @BeforeEach
    void init() {
        validationContext = new ValidationContext();
        validator = new FirstnameValidator();
    }

    @Test
    void when_firstnameNull_then_addError() {
        UserCreateDto user = UserCreateDto.builder().build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Не введено имя или состоит из пробельных символов");
    }

    @Test
    void when_firstnameEmpty_then_addError() {
        UserCreateDto user = UserCreateDto.builder().setFirstname("  ").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Не введено имя или состоит из пробельных символов");
    }

    @Test
    void when_firstnameTooShort_then_addError() {
        UserCreateDto user = UserCreateDto.builder().setFirstname("Ab").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Длина имени должна быть от 3 до 20 символов");
    }

    @Test
    void when_firstnameTooLong_then_addError() {
        UserCreateDto user = UserCreateDto.builder().setFirstname("toolongnametoolongname").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Длина имени должна быть от 3 до 20 символов");
    }

    @Test
    void when_firstnameValid_then_noError() {
        UserCreateDto user = UserCreateDto.builder().setFirstname("Ivan").build();

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).isEmpty();
    }
}