package ru.aston.hometask.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.dao.api.IUserDao;
import ru.aston.hometask.dao.entity.UserEntity;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.api.IUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ValidatorChainTest {
    @Mock
    private IUserDao userDao;
    private IUserValidator chain;
    private ValidationContext context;

    @BeforeEach
    void init() {
        context = new ValidationContext();
        chain = ValidatorChain.build(userDao);
    }

    @Test
    void when_chainAllValid_then_noErrors() {
        UserCreateDto user = UserCreateDto.builder()
                .setPassword("54321")
                .setEmail("example@mail.com")
                .setFirstname("Ivan")
                .setLastname("Ivanov")
                .setPassword("secret")
                .setBirthdate("2000-01-01")
                .build();
        doReturn(Optional.empty()).when(userDao).findByEmail(user.getEmail());

        chain.handle(user, context);
        List<ValidationError> errors = context.getErrors();

        assertThat(errors).isEmpty();
    }

    @Test
    void when_chainAllInValid_then_noErrors() {
        UserCreateDto user = UserCreateDto.builder().build();
        doReturn(Optional.of(UserEntity.builder().build())).when(userDao).findByEmail(user.getEmail());

        chain.handle(user, context);
        List<ValidationError> errors = context.getErrors();

        assertThat(errors).hasSize(6);
    }
}