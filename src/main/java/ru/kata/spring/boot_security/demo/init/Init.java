package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class Init {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Создаю админа и роли
    @PostConstruct
    @Transactional
    public void doInit() {
        if (userRepository.findAll().size() == 0) {
            User user = new User("Leo", "Bonhart", 30, "lb@mail.ru", "admin");
            User user2 = new User("Joe", "Louis", 35, "jl@mail.ru", "user");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user2.setPassword(passwordEncoder.encode(user2.getPassword()));
            Role roleUser = new Role("ROLE_USER");
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);
            userRepository.save(user);
            userRepository.save(user2);
            user.getRoleList().add(roleRepository.findRoleByRole("ROLE_ADMIN"));
            user.getRoleList().add(roleRepository.findRoleByRole("ROLE_USER"));

            user2.getRoleList().add(roleRepository.findRoleByRole("ROLE_USER"));
            userRepository.save(user);
            userRepository.save(user2);

        }
    }
}
