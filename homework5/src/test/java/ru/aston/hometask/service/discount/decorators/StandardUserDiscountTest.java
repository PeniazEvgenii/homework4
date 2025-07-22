package ru.aston.hometask.service.discount.decorators;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ru.aston.hometask.discount.decorators.StandardUserDiscount;
import ru.aston.hometask.service.dto.UserCreateDto;

class StandardUserDiscountTest {

    @Test
    void when_calculateWithAllUser_then_returnFixedDiscount() {
        StandardUserDiscount discount = new StandardUserDiscount();
        UserCreateDto user = UserCreateDto.builder().build();
        double expectedResult = 1.5;

        double actualResult = discount.calculate(user);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}