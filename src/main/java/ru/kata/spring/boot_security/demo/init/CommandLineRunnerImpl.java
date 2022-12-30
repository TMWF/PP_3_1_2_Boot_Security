package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // @Autowired
    public CommandLineRunnerImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void run(String... arg) throws Exception {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        Set<Role> adminRoles = new HashSet<>();
        Set<Role> userRoles = new HashSet<>();
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        adminRoles.add(roleAdmin);
        adminRoles.add(roleUser);
        userRoles.add(roleUser);


        // пользователи Admin  и User
        User userAdmin = new User("Leo", "Bonhart", "lb@mail.ru", "$2a$12$BzgYbgmoIXM73n14QVilw.XdNlHn0XrOjktbLbzXQGmxgKQbXHL9e", adminRoles);
        User userUser = new User("Joe", "Louis", "joelouis@mail.ru", "$2a$12$BzgYbgmoIXM73n14QVilw.XdNlHn0XrOjktbLbzXQGmxgKQbXHL9e", userRoles);
        System.out.println(userAdmin);
        userRepository.save(userAdmin);
        System.out.println(userUser);
        userRepository.save(userUser);

    }

}
