package com.elearning.services.users;

import com.elearning.dto.RoleDto;
import com.elearning.dto.mapper.MapperDto;
import com.elearning.model.authentication.Role;
import com.elearning.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl {

    private final RoleRepository roleRepository;
    private final MapperDto mapper;

    public RoleServiceImpl(RoleRepository roleRepository, MapperDto mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

//    public ModelAndView getAllRoles() {
//        ModelAndView mav = new ModelAndView("userForm");
//        mav.addObject("roles", roleRepository.findAll());
//        return mav;
//    }
    public Set<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream().map(mapper::convertToRoleDto).collect(Collectors.toSet());
    }
    public List<String> getRoleNames(){
        return roleRepository.findAll().stream().map(Role::getName).collect(Collectors.toList());
    }
    public List<Long> getRoleIds(){
        return roleRepository.findAll().stream().map(Role::getId).collect(Collectors.toList());
    }
//    public HashSet getAllRoles1() {
//        return new HashSet<>(roleRepository.findAll().stream().map(mapper::convertToRoleDto));
//    }
}
