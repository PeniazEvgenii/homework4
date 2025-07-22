package ru.aston.hometask.validator;

import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.api.IUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;

public abstract class BaseUserValidator implements IUserValidator {
    private BaseUserValidator next;

    public BaseUserValidator setNext(BaseUserValidator next) {
        this.next = next;
        return next;
    }

    @Override
    public void handle(UserCreateDto dto, ValidationContext context) {
        validate(dto, context);
        if(next != null) {
            next.handle(dto, context);
        }
    }
}
