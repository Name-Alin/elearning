package com.elearning.controllers;

import com.elearning.dto.RoleDto;
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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@Valid UserDto user, BindingResult result, HttpServletResponse httpServletResponse) throws Exception {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
        }
        log.info("Username: {}", user.getUsername());
        log.info("Role: {}", user.getRoles());
        userService.createNewUser(user);

        httpServletResponse.sendRedirect("/userform");
        return "redirect:/userform";
    }

    @GetMapping("/deleteUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable("id") Long id, HttpServletResponse httpServletResponse) throws IOException {
        userService.deleteUserById(id);
        httpServletResponse.sendRedirect("/userform");
        return "redirect:/userform";
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid UserDto userDto, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
        }

        userService.createNewUser(userDto);

        return "redirect:/userform";
    }

    @GetMapping("showUserById/{id}")
    public String showUserById(@PathVariable("id") Long id, @Valid UserDto userDto, Model model) {

        model.addAttribute("specificUser", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "userUpdateForm";
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
