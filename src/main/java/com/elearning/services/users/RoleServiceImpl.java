package com.elearning.services.users;

import com.elearning.model.authentication.Role;
import com.elearning.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

//    public ModelAndView getAllRoles() {
//        ModelAndView mav = new ModelAndView("userForm");
//        mav.addObject("roles", roleRepository.findAll());
//        return mav;
//    }
    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }
}
