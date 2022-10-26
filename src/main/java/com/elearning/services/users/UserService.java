package com.elearning.services.users;

import com.elearning.dto.UserDto;

import java.util.List;

public interface UserService {

    void saveOrUpdateUser(UserDto user) throws Exception;

    List<UserDto> getAllUsers();

    void deleteUserById(Long id);

    UserDto getUserById(Long id);

    List<UserDto> getSupervisorsNames();
}
