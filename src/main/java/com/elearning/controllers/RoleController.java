package com.elearning.controllers;

import com.elearning.model.authentication.Role;
import com.elearning.services.users.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Slf4j
@Controller
public class RoleController {

    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getRoles")
    public Set<Role> getRoles(Model model) {
        return roleService.getAllRoles();
//        model.addAttribute("roles",roleService.getAllRoles());
//        return model;
    }
}
