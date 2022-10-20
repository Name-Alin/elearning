package com.elearning.services.users;

import com.elearning.dto.UserDto;
import com.elearning.dto.mapper.MapperDto;
import com.elearning.model.authentication.Role;
import com.elearning.model.authentication.User;
import com.elearning.repositories.RoleRepository;
import com.elearning.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapperDto mapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MapperDto mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void createNewUser(UserDto userDto) throws Exception {
        User user = mapper.convertToUserEntity(userDto);
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new Exception("Username: "+user.getUsername()+" is already present in database");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = userRepository.save(user);

        log.info("New user added: {} #ID{}", userSaved.getUsername(), userSaved.getId());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
