package com.elearning.dto.mapper;

import com.elearning.dto.RoleDto;
import com.elearning.dto.UserDto;
import com.elearning.model.authentication.Role;
import com.elearning.model.authentication.User;
import org.springframework.stereotype.Component;

@Component
public class MapperDto {

    public UserDto convertToUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .roles(user.getRoles())
                .build();
    }
    public User convertToUserEntity(UserDto userDto){
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .enabled(userDto.isEnabled())
                .roles(userDto.getRoles())
                .build();
    }

    public Role convertToRoleEntity(RoleDto roleDto){
        if (roleDto == null)
            return null;

       return Role.builder().name(roleDto.getName()).build();
    }

    public RoleDto convertToRoleDto(Role role){
        if (role == null)
            return null;

        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}