package ru.aston.hometask.discount.decorators;

import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.service.dto.UserCreateDto;

import java.time.LocalTime;

public class NightRegistrationDiscount extends BaseDiscountDecorator {
    private static final int START_HOUR = 1;
    private static final int END_HOUR = 5;
    private static final double SIZE_DISCOUNT = 1;

    public NightRegistrationDiscount(IUserDiscount discount) {
        super(discount);
    }

    @Override
    public double calculate(UserCreateDto user) {
        double discount = super.calculate(user);
        int hour = LocalTime.now().getHour();

        if(hour >= START_HOUR && hour < END_HOUR) {
            discount += SIZE_DISCOUNT;
        }
        return discount;
    }
}
