package com.elearning.services.authentication;

import com.elearning.domain.authentication.Role;
import com.elearning.domain.authentication.User;
import com.elearning.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("The username: " + username + " was not found!");
        }
        user.get().getRoles().stream().iterator().forEachRemaining(l->log.info(l.getName()));
        return new MyUserDetails(user);
    }

//    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
//        return getGrantedAuthorities(roles);
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(Collection<Role> roles) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return authorities;
//    }
}
