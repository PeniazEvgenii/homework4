package ru.aston.hometask.validator.valid;

import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.BaseUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

public class LastnameValidator extends BaseUserValidator {
    private static final String ERROR_NAME = "Неверно указана фамилия";
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    @Override
    public void validate(UserCreateDto dto, ValidationContext context) {
        String lastname = dto.getLastname();
        if (lastname == null || lastname.isBlank()) {
            context.addError(new ValidationError(ERROR_NAME, "Не введена фамилия или состоит из пробельных символов"));
        } else if (lastname.length() < MIN_LENGTH || lastname.length() > MAX_LENGTH) {
            context.addError(new ValidationError(ERROR_NAME, "Длина фамилии должна быть от 3 до 20 символов"));
        }
    }
}
