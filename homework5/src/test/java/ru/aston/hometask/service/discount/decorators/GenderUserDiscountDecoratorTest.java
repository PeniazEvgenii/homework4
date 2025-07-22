package ru.aston.hometask.service.discount.decorators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.discount.decorators.GenderDiscountDecorator;
import ru.aston.hometask.service.dto.EGender;
import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.service.dto.UserCreateDto;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenderUserDiscountDecoratorTest {
    private static final double FIXED_DISCOUNT = 0;
    private static final double GENDER_DISCOUNT = 2.0;

    @Mock
    private IUserDiscount discount;
    @InjectMocks
    private GenderDiscountDecorator genderDecorator;

    @Test
    void when_calculateForFemale_then_addGenderDiscount() {
        doReturn(FIXED_DISCOUNT).when(discount).calculate(any());
        UserCreateDto user = UserCreateDto.builder().setGender(EGender.FEMALE).build();

        double actualResult = genderDecorator.calculate(user);

        assertThat(actualResult).isEqualTo(GENDER_DISCOUNT);
    }

    @Test
    void when_calculateForMale_then_noGenderDiscount() {
        doReturn(FIXED_DISCOUNT).when(discount).calculate(any());
        UserCreateDto user = UserCreateDto.builder().setGender(EGender.MALE).build();

        double actualResult = genderDecorator.calculate(user);

        assertThat(actualResult).isEqualTo(FIXED_DISCOUNT);
    }
}