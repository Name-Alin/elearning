package com.elearning.services.users;

import com.elearning.dto.UserDto;
import com.elearning.dto.mapper.MapperDto;
import com.elearning.exceptions.ErrorType;
import com.elearning.exceptions.ResourceNotFoundException;
import com.elearning.model.authentication.User;
import com.elearning.repositories.EvaluationDetailsRepository;
import com.elearning.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapperDto mapper;

    @Autowired
    EvaluationDetailsRepository evaluationDetailsRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MapperDto mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void saveOrUpdateUser(UserDto userDto) {
        User user = mapper.convertToUserEntity(userDto);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            throw new Exception("Username: " + user.getUsername() + " is already present in database");
            user.setEvaluationDetails(evaluationDetailsRepository.userEvaluations(user));
        }
//        if (user.getId() == null) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
        if (user.getPassword().chars().count() == 60) {
            log.info("Password count is: {}", user.getPassword().chars().count());
            user.setPassword(user.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        User userSaved = userRepository.save(user);

        log.info("New user added: {} #ID{}", userSaved.getUsername(), userSaved.getId());
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(mapper::convertToUserDto).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User was not found", ErrorType.USER_DOES_NOT_EXIST));
        userRepository.delete(user);

    }

    @Override
    @Transactional
    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(mapper::convertToUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("User was not found", ErrorType.USER_DOES_NOT_EXIST));
    }

    @Transactional
    @Override
    public List<UserDto> getSupervisorsNames() {
        return userRepository.getSupervisors().stream().map(mapper::convertToUserDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersSupervisedBy(String userName) {
        User user = userRepository.findByUsername(userName).get();
        return userRepository.getUsersSupervisedBy(user.getId()).stream().map(mapper::convertToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByName(String userName) {
        User user = userRepository.findByUsername(userName).get();
        return mapper.convertToUserDto(user);
    }
}
