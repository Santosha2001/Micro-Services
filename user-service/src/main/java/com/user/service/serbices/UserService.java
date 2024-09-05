package com.user.service.serbices;

import com.user.service.entities.User;

import java.util.List;

public interface UserService {

    // create user
    User saveUser(User user);

    //get all users
    List<User> getAllUsers();

    //get single user by id
    User getUserById(String id);
}
