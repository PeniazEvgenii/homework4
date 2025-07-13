package ru.aston.hometask.validator.api;

import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.error.ValidationContext;

public interface IUserValidator {
    void validate(UserCreateDto dto, ValidationContext context);
    void handle(UserCreateDto dto, ValidationContext context);
}
