package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class Controllers {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public Controllers(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("admin/users")
    public String showAllUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currentUser", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "showAllUsers";
    }

    @GetMapping("/user")
    public String showOneUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("currentUser", user);
        return "showOneUser";
    }

    @PostMapping("admin/new")
    public String createUser(@ModelAttribute User user) {
        userService.createNewUser(user);
        return "redirect:/admin/users";
    }

    @PatchMapping("/admin/user/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user) {
        System.out.println(user);
        userService.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
