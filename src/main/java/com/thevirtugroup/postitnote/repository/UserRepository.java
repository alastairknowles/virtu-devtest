package com.thevirtugroup.postitnote.repository;

import static com.thevirtugroup.postitnote.Constants.DEFAULT_USER_ID;
import static com.thevirtugroup.postitnote.Constants.DEFAULT_USER_PASSWORD;
import static com.thevirtugroup.postitnote.Constants.DEFAULT_USER_USERNAME;

import com.thevirtugroup.postitnote.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final User defaultUser;

    public UserRepository() {
        defaultUser = new User();
        defaultUser.setId(DEFAULT_USER_ID);
        defaultUser.setPassword(DEFAULT_USER_PASSWORD);
        defaultUser.setUsername(DEFAULT_USER_USERNAME);
    }

    public User findUserByUsername(String username) {
        if (DEFAULT_USER_USERNAME.equals(username)) {
            return defaultUser;
        }
        return null;
    }

    public User findById(Long id) {
        if (defaultUser.getId().equals(id)) {
            return defaultUser;
        }
        return null;
    }

}