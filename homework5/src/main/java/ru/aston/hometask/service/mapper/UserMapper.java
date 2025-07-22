package ru.aston.hometask.service.mapper;

import ru.aston.hometask.dao.entity.UserEntity;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.service.dto.UserReadDto;
import ru.aston.hometask.service.mapper.api.IUserMapper;
import ru.aston.hometask.util.DateUtil;

import java.time.OffsetDateTime;
import java.util.UUID;

public class UserMapper implements IUserMapper {

    @Override
    public UserEntity toEntity(UserCreateDto dto, double discount) {
        return UserEntity.builder()
                .setId(UUID.randomUUID())
                .setEmail(dto.getEmail())
                .setFirstname(dto.getFirstname())
                .setLastname(dto.getLastname())
                .setBirthDate(DateUtil.parseDateFromString(dto.getBirthdate()))
                .setGender(dto.getGender())
                .setPassword(dto.getPassword())           //bcrypt
                .setDiscount(discount)
                .setDtCreate(OffsetDateTime.now())
                .setDtUpdate(OffsetDateTime.now())
                .build();
    }

    @Override
    public UserReadDto toDto(UserEntity entity) {
        return UserReadDto.builder()
                .setId(UUID.randomUUID())
                .setEmail(entity.getEmail())
                .setFirstname(entity.getFirstname())
                .setLastname(entity.getLastname())
                .setBirthDate(entity.getBirthDate())
                .setGender(entity.getGender())
                .setPassword(entity.getPassword())
                .setDiscount(entity.getDiscount())
                .setDtCreate(entity.getDtCreate())
                .setDtUpdate(entity.getDtUpdate())
                .build();
    }
}
