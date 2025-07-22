package ru.aston.hometask.discount.api;

import ru.aston.hometask.service.dto.UserCreateDto;

public interface IUserDiscount {
    double calculate(UserCreateDto user);
}
