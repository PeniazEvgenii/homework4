package ru.aston.hometask.discount.decorators;

import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.util.DateUtil;

public class AgeDiscountDecorator extends BaseDiscountDecorator {
    private static final int AGE_THRESHOLD = 60;
    private static final double SIZE_AGE_DISCOUNT = 5.0;

    public AgeDiscountDecorator(IUserDiscount discount) {
        super(discount);
    }

    @Override
    public double calculate(UserCreateDto user) {
        double discount = super.calculate(user);

        int age = DateUtil.getAge(user.getBirthdate());
        if (age >= AGE_THRESHOLD) {
            discount += SIZE_AGE_DISCOUNT;
        }

        return discount;
    }
}
