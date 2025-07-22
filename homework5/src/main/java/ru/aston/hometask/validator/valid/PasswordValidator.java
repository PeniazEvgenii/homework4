package ru.aston.hometask.validator.valid;

import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.BaseUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

public class PasswordValidator extends BaseUserValidator {
    private static final String ERROR_NAME = "Неверный пароль";
    private static final int MIN_LENGTH_PASSWORD = 4;

    @Override
    public void validate(UserCreateDto dto, ValidationContext context) {
        String password = dto.getPassword();

        if (password == null || password.isBlank()) {
            context.addError(new ValidationError(ERROR_NAME, "Не введен пароль или состоит из пробельных символов"));
        } else if (password.length() < MIN_LENGTH_PASSWORD) {
            context.addError(new ValidationError(ERROR_NAME, "Длина пароля должна быть не менее 4 символов"));
        }
    }
}
