package ru.aston.hometask.dao;

import ru.aston.hometask.dao.api.IUserDao;
import ru.aston.hometask.dao.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserDaoProxy implements IUserDao {
    private final IUserDao userDao;
    private final Map<UUID, UserEntity> cacheId = new HashMap<>();
    private final Map<String, UserEntity> cacheEmail = new HashMap<>();
    private List<UserEntity> cacheAllUser = new ArrayList<>();
    private boolean isValidCacheAll = false;

    public UserDaoProxy(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserEntity create(UserEntity user) {
        UserEntity savedUser = userDao.create(user);
        cacheId.put(savedUser.getId(), savedUser);
        cacheEmail.put(savedUser.getEmail(), savedUser);
        isValidCacheAll = false;
        return savedUser;
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        UserEntity cashedUser = cacheId.get(id);
        if (cashedUser != null) {
            return Optional.of(cashedUser);
        }
        Optional<UserEntity> maybeUser = userDao.findById(id);
        maybeUser.ifPresent(userEntity -> cacheId.put(id, userEntity));
        return maybeUser;
    }

    @Override
    public List<UserEntity> findAll() {
        if (isValidCacheAll) {
            return cacheAllUser;
        }
        List<UserEntity> users = userDao.findAll();
        cacheId.clear();
        cacheEmail.clear();
        cacheAllUser.clear();

        cacheAllUser = Collections.unmodifiableList(users);
        isValidCacheAll = true;

        for (UserEntity user : users) {
            cacheId.put(user.getId(), user);
            cacheEmail.put(user.getEmail(), user);
        }
        return users;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        UserEntity userCached = cacheEmail.get(email);
        if(userCached != null) {
            return Optional.of(userCached);
        }
        Optional<UserEntity> maybeUser = userDao.findByEmail(email);
        maybeUser.ifPresent(userEntity -> cacheEmail.put(email, userEntity));
        return maybeUser;
    }

    @Override
    public boolean delete(UserEntity user) {
        boolean isDeleted = userDao.delete(user);
        if (isDeleted) {
            cacheId.remove(user.getId());
            cacheEmail.remove(user.getEmail());
            isValidCacheAll = false;
        }
        return isDeleted;
    }
}
