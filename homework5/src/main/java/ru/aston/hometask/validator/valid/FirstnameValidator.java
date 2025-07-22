package ru.aston.hometask.validator.valid;

import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.BaseUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

public class FirstnameValidator extends BaseUserValidator {
    private static final String ERROR_NAME = "Неверное имя";
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    @Override
    public void validate(UserCreateDto dto, ValidationContext context) {
        String firstname = dto.getFirstname();
        if (firstname == null || firstname.isBlank()) {
            context.addError(new ValidationError(ERROR_NAME, "Не введено имя или состоит из пробельных символов"));
        } else if (firstname.length() < MIN_LENGTH || firstname.length() > MAX_LENGTH) {
            context.addError(new ValidationError(ERROR_NAME, "Длина имени должна быть от 3 до 20 символов"));
        }
    }
}
