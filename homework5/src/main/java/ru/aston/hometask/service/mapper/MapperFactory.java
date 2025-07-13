package ru.aston.hometask.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.aston.hometask.service.mapper.api.IUserMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperFactory {
    private static final IUserMapper USER_MAPPER = new UserMapper();

    public static IUserMapper getUserMapper() {
        return USER_MAPPER;
    }
}
