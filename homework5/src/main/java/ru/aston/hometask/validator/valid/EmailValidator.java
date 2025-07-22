package ru.aston.hometask.validator.valid;

import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.BaseUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

public class EmailValidator extends BaseUserValidator {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String ERROR_NAME = "Ошибка электронного адреса";

    @Override
    public void validate(UserCreateDto dto, ValidationContext context) {
        String email = dto.getEmail();

        if (email == null || email.isBlank()) {
            context.addError(new ValidationError(ERROR_NAME, "Не указан электронный адрес"));
        } else if(!email.matches(EMAIL_PATTERN)) {
            context.addError(new ValidationError(ERROR_NAME, "Указан неверный формат электронного адреса"));
        }
    }
}
