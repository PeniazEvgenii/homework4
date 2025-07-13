package ru.aston.hometask.service.api;

import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.service.dto.UserReadDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    UserReadDto create(UserCreateDto user);
    Optional<UserReadDto> findById(UUID id);
    Optional<UserReadDto> findByEmail(String email);
    List<UserReadDto> findAll();
    boolean delete(UUID id);
}
