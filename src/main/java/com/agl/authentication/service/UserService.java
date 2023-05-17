package com.agl.authentication.service;

import com.agl.authentication.persistence.UserDao;
import com.agl.authentication.persistence.entity.UserEntity;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Singleton
@Slf4j
public class UserService {
    @Inject
    private UserDao userDao;

    @Inject
    private PasswordEncryptor passwordEncryptor;

    public UserService(UserDao userDao, PasswordEncryptor passwordEncryptor) {
        this.userDao = userDao;
        this.passwordEncryptor = passwordEncryptor;
    }

    public Boolean authenticate(String username, String password) {

        UserEntity user = null;
        try {
            user = userDao.retrieveUserByUsername(username);
        } catch (Exception e) {
            return Boolean.FALSE;
        }

        if (!Objects.isNull(user) && passwordEncryptor.matches(password, user.getPassword())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void register(final String username, final String password) {

        checkForExistingUser(username);

        String encryptedPassword = passwordEncryptor.encode(password);

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(encryptedPassword)
                .build();

        userDao.create(userEntity);
    }

    public User retrieveUser(String username) {
        try {
             return mapUser(userDao.retrieveUserByUsername(username));
        } catch (Exception e) {
            log.error("Unable to retrieve User");
            throw new HttpClientResponseException("Unable to retrieve User", HttpResponse.badRequest());
        }
    }

    private User mapUser (UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId().toString())
                .username(userEntity.getUsername())
                .build();
    }

    private void checkForExistingUser(String username) {
        try {
            UserEntity userEntity = userDao.retrieveUserByUsername(username);
            if (!Objects.isNull(userEntity)) {
                log.info("User already exists");
                throw new HttpStatusException(HttpStatus.BAD_GATEWAY, "user already exists");
            }
        } catch (Exception e) {
            throw new HttpStatusException(HttpStatus.BAD_GATEWAY, "Could not save user to DB");
        }
    }

}
