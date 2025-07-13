package ru.aston.hometask.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.hometask.dao.entity.UserEntity;
import ru.aston.hometask.service.dto.EGender;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.service.dto.UserReadDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    private static final String FIRSTNAME = "IVAN";
    private static final String LASTNAME = "IVANOV";
    private static final String PASSWORD = "SECRET";
    private static final String EMAIL = "example@mail.com";
    private static final String BIRTHDATE = "1980-01-01";
    private static final double DISCOUNT = 5.5;

    private UserMapper userMapper;

    @BeforeEach
    void init() {
        userMapper = new UserMapper();
    }

    @Test
    void when_mapDto_then_returnEntity() {
        UserCreateDto userCreate = getUserCreateDto();
        UserEntity expectedEntity = getUserEntity();

        UserEntity actualEntity = userMapper.toEntity(userCreate, DISCOUNT);

        assertAll(
                () -> assertEquals(expectedEntity.getEmail(), actualEntity.getEmail()),
                () -> assertEquals(expectedEntity.getFirstname(), actualEntity.getFirstname()),
                () -> assertEquals(expectedEntity.getLastname(), actualEntity.getLastname()),
                () -> assertEquals(expectedEntity.getGender(), actualEntity.getGender()));
    }

    @Test
    void when_mapEntity_then_returnUserReadDto() {
        UserEntity entity = getUserEntity();
        UserReadDto expectedUser = getUserReadDto();

        UserReadDto actualUser = userMapper.toDto(entity);

        assertAll(
                () -> assertEquals(expectedUser.getEmail(), actualUser.getEmail()),
                () -> assertEquals(expectedUser.getFirstname(), actualUser.getFirstname()),
                () -> assertEquals(expectedUser.getDtCreate(), actualUser.getDtCreate()),
                () -> assertEquals(expectedUser.getDtUpdate(), actualUser.getDtUpdate()),
                () -> assertEquals(expectedUser.getDiscount(), actualUser.getDiscount()),
                () -> assertEquals(expectedUser.getLastname(), actualUser.getLastname()),
                () -> assertEquals(expectedUser.getGender(), actualUser.getGender()));
    }

    private static UserCreateDto getUserCreateDto() {
        return UserCreateDto.builder()
                .setFirstname(FIRSTNAME)
                .setLastname(LASTNAME)
                .setPassword(PASSWORD)
                .setEmail(EMAIL)
                .setGender(EGender.MALE)
                .setBirthdate(BIRTHDATE)
                .build();
    }

    private static UserEntity getUserEntity() {
        return UserEntity.builder()
                .setFirstname(FIRSTNAME)
                .setLastname(LASTNAME)
                .setPassword(PASSWORD)
                .setEmail(EMAIL)
                .setGender(EGender.MALE)
                .setBirthDate(LocalDate.of(1980, 1, 1))
                .setDiscount(DISCOUNT)
                .build();
    }

    private static UserReadDto getUserReadDto() {
        return UserReadDto.builder()
                .setFirstname(FIRSTNAME)
                .setLastname(LASTNAME)
                .setPassword(PASSWORD)
                .setEmail(EMAIL)
                .setGender(EGender.MALE)
                .setBirthDate(LocalDate.of(1980, 1, 1))
                .setDiscount(DISCOUNT)
                .build();
    }
}