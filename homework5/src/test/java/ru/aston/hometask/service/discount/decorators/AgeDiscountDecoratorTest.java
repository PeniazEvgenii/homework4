package ru.aston.hometask.service.discount.decorators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.discount.decorators.AgeDiscountDecorator;
import ru.aston.hometask.service.dto.UserCreateDto;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgeDiscountDecoratorTest {
    private static final double FIXED_DISCOUNT = 2.0;
    private static final double AGE_DISCOUNT = 5.0;

    @Mock
    private IUserDiscount discount;
    @InjectMocks
    private AgeDiscountDecorator ageDecorator;

    @Test
    void when_ageAtOrAboveThreshold_then_addAgeDiscount() {
        when(discount.calculate(any())).thenReturn(FIXED_DISCOUNT);
        UserCreateDto user = UserCreateDto.builder().setBirthdate("1900-01-01").build();

        double actualResult = ageDecorator.calculate(user);

        assertThat(actualResult).isEqualTo(FIXED_DISCOUNT + AGE_DISCOUNT);
    }

    @Test
    void when_ageBelowThreshold_then_noAgeDiscount() {
        when(discount.calculate(any())).thenReturn(FIXED_DISCOUNT);
        UserCreateDto user = UserCreateDto.builder().setBirthdate("2000-01-01").build();

        double actualResult = ageDecorator.calculate(user);

        assertThat(actualResult).isEqualTo(FIXED_DISCOUNT);
    }
}