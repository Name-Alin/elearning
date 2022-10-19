package com.elearning.services.users;

import com.elearning.model.authentication.User;
import com.elearning.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createNewUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

//    private void extracted() {
//        Iterable<Role> allRoles = roleRepository.findAll();
//        allRoles.forEach(l->
//                {
//                    Set<Role> roles = new HashSet<>();
//                    roles.add(l);
//                }
//        );
//    }
}
