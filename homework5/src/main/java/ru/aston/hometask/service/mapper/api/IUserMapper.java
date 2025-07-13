package ru.aston.hometask.service.mapper.api;

import ru.aston.hometask.dao.entity.UserEntity;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.service.dto.UserReadDto;

public interface IUserMapper {
    UserEntity toEntity(UserCreateDto dto, double discount);
    UserReadDto toDto(UserEntity entity);

}
