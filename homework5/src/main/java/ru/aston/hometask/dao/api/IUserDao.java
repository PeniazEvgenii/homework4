package ru.aston.hometask.dao.api;

import ru.aston.hometask.dao.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserDao {
    UserEntity create(UserEntity user);
    Optional<UserEntity> findById(UUID id);
    List<UserEntity> findAll();
    Optional<UserEntity> findByEmail(String email);
    boolean delete(UserEntity user);
}
