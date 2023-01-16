package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class Controllers {

    private final UserService userService;

    @Autowired
    public Controllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("admin/users")
    public String showAllUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("currentUser", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", userService.getRole());
        return "showAllUsers";
    }

    @GetMapping("/user")
    public String showOneUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("currentUser", user);
        return "showOneUser";
    }

    @PostMapping("admin/new")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PatchMapping("/admin/user/edit/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/user/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
