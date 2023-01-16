package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private  final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
    return userRepository.findAll();
    }


    public void createNewUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }


    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public void updateUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void update(Long id, User updatedUser) {
        updatedUser.setId(id);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
//        if (updatedUser.getRoles().contains(roleRepositories.findRoleByRole("ROLE_ADMIN"))) {
//            updatedUser.getRoleList().add(roleRepositories.findRoleByRole("ROLE_USER"));
//        }
        userRepository.save(updatedUser);
    }


    public void deleteUser(Long id) {
        userRepository.delete(userRepository.findById(id).get());
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
