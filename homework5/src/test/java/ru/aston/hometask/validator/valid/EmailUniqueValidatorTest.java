package ru.aston.hometask.validator.valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.dao.api.IUserDao;
import ru.aston.hometask.dao.entity.UserEntity;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.validator.error.ValidationContext;
import ru.aston.hometask.validator.error.ValidationError;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailUniqueValidatorTest {
    private ValidationContext validationContext;
    @Mock
    private IUserDao userDao;
    @InjectMocks
    private EmailUniqueValidator validator;

    @BeforeEach
    void init() {
        validationContext = new ValidationContext();
    }

    @Test
    void when_emailNotExist_then_noError() {
        UserCreateDto user = UserCreateDto.builder().setEmail("example@mail.com").build();
        doReturn(Optional.empty()).when(userDao).findByEmail(anyString());

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).isEmpty();
        verify(userDao).findByEmail(anyString());
    }

    @Test
    void when_emailExist_then_addError() {
        UserCreateDto user = UserCreateDto.builder().setEmail("example@mail.com").build();
        doReturn(Optional.of(UserEntity.builder().build())).when(userDao).findByEmail(user.getEmail());

        validator.validate(user, validationContext);
        List<ValidationError> errors = validationContext.getErrors();

        assertThat(errors).hasSize(1)
                .extracting(ValidationError::description)
                .containsExactly("Указанный электронный адрес уже зарегистрирован");
        verify(userDao).findByEmail(anyString());
    }
}