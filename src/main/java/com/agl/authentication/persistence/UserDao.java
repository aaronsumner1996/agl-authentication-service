package com.agl.authentication.persistence;

import com.agl.authentication.persistence.entity.UserEntity;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class UserDao extends AbstractDao {

    @TransactionalAdvice
    @Transactional
    public UserEntity create(UserEntity user) {
        entityManager.persist(user);
        return user;
    }

    @TransactionalAdvice
    public UserEntity retrieveUserByUsername(final String username) throws Exception {
        return findOne("SELECT users FROM UserEntity users WHERE users.username=:username",
                UserEntity.class,
                "username", username);
    }

}
