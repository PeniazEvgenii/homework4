package ru.aston.hometask.validator.valid;

import lombok.RequiredArgsConstructor;
import ru.aston.hometask.dao.api.IUserDao;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.BaseUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

@RequiredArgsConstructor
public class EmailUniqueValidator extends BaseUserValidator {
    private static final String ERROR_NAME = "Ошибка электронного адреса";
    private static final String ERROR_DESCRIPTION = "Указанный электронный адрес уже зарегистрирован";

    private final IUserDao userDao;

    @Override
    public void validate(UserCreateDto dto, ValidationContext context) {
        userDao.findByEmail(dto.getEmail())
                .ifPresent(userReadDto ->
                        context.addError(new ValidationError(ERROR_NAME, ERROR_DESCRIPTION)));
    }
}
