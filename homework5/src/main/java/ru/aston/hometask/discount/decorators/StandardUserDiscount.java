package ru.aston.hometask.discount.decorators;

import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.service.dto.UserCreateDto;

public class StandardUserDiscount implements IUserDiscount {
    private static final double STANDARD_DISCOUNT = 1.5;

    @Override
    public double calculate(UserCreateDto user) {
        return STANDARD_DISCOUNT;
    }
}
