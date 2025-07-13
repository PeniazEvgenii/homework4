package ru.aston.hometask.service.discount.decorators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.discount.decorators.NightRegistrationDiscount;
import ru.aston.hometask.service.dto.UserCreateDto;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class NightRegistrationUserDiscountTest {
    private static final double FIXED_DISCOUNT = 0;
    private static final double NIGHT_DISCOUNT = 1.0;
    private static final LocalTime FIXED_NIGHT_TIME = LocalTime.of(2, 30);
    private static final LocalTime FIXED_DAY_TIME = LocalTime.of(12, 30);

    @Mock
    private IUserDiscount discount;
    @InjectMocks
    private NightRegistrationDiscount nightDiscount;

    @Test
    void when_calculateWithinWindowTime_then_addNightDiscount() {
        doReturn(FIXED_DISCOUNT).when(discount).calculate(any());

        try (MockedStatic<LocalTime> mt = mockStatic(LocalTime.class)) {
            mt.when(LocalTime::now).thenReturn(FIXED_NIGHT_TIME);
            UserCreateDto user = UserCreateDto.builder().build();

            double result = nightDiscount.calculate(user);

            assertThat(result).isEqualTo(NIGHT_DISCOUNT + FIXED_DISCOUNT);
        }
    }

    @Test
    void when_calculateOutsideWindowTime_then_noNightDiscount() {
        doReturn(FIXED_DISCOUNT).when(discount).calculate(any());

        try (MockedStatic<LocalTime> mt = mockStatic(LocalTime.class)) {
            mt.when(LocalTime::now).thenReturn(FIXED_DAY_TIME);
            UserCreateDto user = UserCreateDto.builder().build();

            double result = nightDiscount.calculate(user);

            assertThat(result).isEqualTo(FIXED_DISCOUNT);
        }
    }
}