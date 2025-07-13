package ru.aston.hometask.service;

import lombok.RequiredArgsConstructor;
import ru.aston.hometask.dao.api.IUserDao;
import ru.aston.hometask.exception.ValidationException;
import ru.aston.hometask.service.api.IUserService;
import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.service.dto.UserReadDto;
import ru.aston.hometask.service.mapper.api.IUserMapper;
import ru.aston.hometask.validator.api.IUserValidator;
import ru.aston.hometask.validator.error.ValidationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserValidator userValidator;
    private final IUserDiscount userDiscount;
    private final IUserMapper userMapper;
    private final IUserDao userDao;

    /**
     * Создает нового пользователя.
     * Валидация данных проходит через цепочку валидаторов и собирает ошибки в список в ValidationContext.
     * При наличии ошибок выбрасывает {@link ValidationException}
     * Расчет скидки пользователя осуществляется {@link IUserDiscount} с использованием декоратора.
     *
     * @param user dto c введенными пользователем данные
     * @return сохраненный пользователь
     * @throws ValidationException при наличии ошибок в переданных данных
     */
    @Override
    public UserReadDto create(UserCreateDto user) {
        ValidationContext validationContext = new ValidationContext();
        userValidator.handle(user, validationContext);

        if (validationContext.hasError()) {
            throw new ValidationException(validationContext.getErrors());
        }

        double sizeDiscount = userDiscount.calculate(user);

        return Optional.of(user)
                .map(u -> userMapper.toEntity(u, sizeDiscount))
                .map(userDao::create)
                .map(userMapper::toDto)
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public Optional<UserReadDto> findById(UUID id) {
        return userDao.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public Optional<UserReadDto> findByEmail(String email) {
        return userDao.findByEmail(email)
                .map(userMapper::toDto);
    }

    @Override
    public List<UserReadDto> findAll() {
        return userDao.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public boolean delete(UUID id) {
        return userDao.findById(id)
                .map(userDao::delete)
                .orElseThrow(() -> new IllegalArgumentException("Пользоваетель с id " + id + " не найден"));
    }
}
