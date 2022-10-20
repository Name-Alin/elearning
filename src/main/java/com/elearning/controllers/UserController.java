package com.elearning.controllers;

import com.elearning.dto.UserDto;
import com.elearning.model.authentication.Role;
import com.elearning.model.authentication.User;
import com.elearning.exceptions.NotFoundException;
import com.elearning.services.users.RoleServiceImpl;
import com.elearning.services.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
//@PreAuthorize("hasRole(ADMIN)")
public class UserController {


    private final UserService userService;
    private final RoleServiceImpl roleService;

    public UserController(UserService userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/userform")
    public String userForm(User user, Role role1, Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "userForm";
    }


    @PostMapping("/adduser")
    public String addUser(@Valid UserDto user, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
        }
        log.info("Username: {}",user.getUsername());
        log.info("Role: {}",user.getRoles());
        userService.createNewUser(user);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
