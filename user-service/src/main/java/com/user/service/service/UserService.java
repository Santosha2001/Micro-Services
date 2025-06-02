package com.user.service.service;

import com.user.service.entities.User;

import java.util.List;

public interface UserService {

    //Create User
    User saveUser(User user);

    //Get Single User
    User getUser(String userId);

    //Get All Users
    List<User> getAllUsers();

    //Delete User
    void deleteUser(String userId);

    //Update User
    User updateUser(String userId, User userDetails);
}
