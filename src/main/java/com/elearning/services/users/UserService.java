package com.elearning.services.users;

import com.elearning.dto.UserDto;
import com.elearning.model.authentication.User;

import java.util.List;

public interface UserService {

    void createNewUser(UserDto user) throws Exception;

    List<User> getAllUsers();
}
