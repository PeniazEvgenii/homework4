package ru.aston.hometask.validator.valid;

import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.util.DateUtil;
import ru.aston.hometask.validator.BaseUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

public class DateValidator extends BaseUserValidator {
    private static final String ERROR_NAME = "некорректная дата";

    @Override
    public void validate(UserCreateDto dto, ValidationContext context) {
        String birthDate = dto.getBirthdate();
        if(birthDate == null || birthDate.isBlank()) {
            context.addError(new ValidationError(ERROR_NAME, "Не введена дата"));
        } else if (!DateUtil.isValidDate(birthDate)) {
            context.addError(new ValidationError(ERROR_NAME, "введенная дата не соответствует формату"));
        }
    }
}
