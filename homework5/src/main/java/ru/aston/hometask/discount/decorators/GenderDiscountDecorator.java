package ru.aston.hometask.discount.decorators;

import ru.aston.hometask.service.dto.EGender;
import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.service.dto.UserCreateDto;

public class GenderDiscountDecorator extends BaseDiscountDecorator {
    private static final double SIZE_DISCOUNT_FEMALE = 2;

    public GenderDiscountDecorator(IUserDiscount discount) {
        super(discount);
    }

    @Override
    public double calculate(UserCreateDto user) {
        double discount = super.calculate(user);
        if (user.getGender().equals(EGender.FEMALE)) {
            discount += SIZE_DISCOUNT_FEMALE;
        }
        return discount;
    }
}
