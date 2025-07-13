package ru.aston.hometask.discount.decorators;

import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.service.dto.UserCreateDto;

public abstract class BaseDiscountDecorator implements IUserDiscount {
    private final IUserDiscount discount;

    protected BaseDiscountDecorator(IUserDiscount discount) {
        this.discount = discount;
    }

    @Override
    public double calculate(UserCreateDto user) {
        return discount.calculate(user);
    }
}
