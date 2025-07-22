package ru.aston.hometask.service.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ru.aston.hometask.discount.DiscountDecoratorFactory;
import ru.aston.hometask.service.dto.EGender;
import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.service.dto.UserCreateDto;

import java.time.LocalTime;


import static org.mockito.Mockito.mockStatic;

class DiscountDecoratorFactoryTest {
    private static final LocalTime FIXED_NIGHT_TIME = LocalTime.of(2, 30);
    private static final double MAX_SUM_DISCOUNT = 9.5;
    @Test
    void when_buildDecoratorChain_then_getAllDecorators() {
        IUserDiscount decorators = DiscountDecoratorFactory.buildDecoratorChain();

        UserCreateDto userDto = UserCreateDto.builder()
                .setGender(EGender.FEMALE)
                .setBirthdate("1950-01-01")
                .build();

        try (MockedStatic<LocalTime> mt = mockStatic(LocalTime.class)) {
            mt.when(LocalTime::now).thenReturn(FIXED_NIGHT_TIME);

            double actualResult = decorators.calculate(userDto);

            Assertions.assertThat(actualResult).isEqualTo(MAX_SUM_DISCOUNT);
        }
    }
}