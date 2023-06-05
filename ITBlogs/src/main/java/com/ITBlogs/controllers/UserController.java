package com.ITBlogs.controllers;

import com.ITBlogs.models.DTO.UserSettingsDto;
import com.ITBlogs.models.User;
import com.ITBlogs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable long id) {
        try {
            return this.userRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot get user with id: " + id);
            return null;
        }
    }

    @PutMapping()
    boolean updateUser(@RequestBody UserSettingsDto userSettings) {
        try {
            var changed = false;
            var user = this.userRepository.findById(userSettings.getId()).get();
            if (userSettings.getUsername() != null){
                user.setUsername(userSettings.getUsername());
                changed = true;
            }
            if (userSettings.getEmail() != null) {
                user.setEmail(userSettings.getEmail());
                changed = true;
            }
            if (userSettings.getPassword() != null) {
                user.setPassword(encoder.encode(userSettings.getPassword()));
                changed = true;
            }
            if (changed) this.userRepository.save(user);
            return true;
        } catch (Exception exception){
            this.logger.warn("Cannot update user with id: " + userSettings.getId());
        }
        return false;
    }

    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable long id) {
        try {
            var user = this.userRepository.findById(id).get();
            user.setDeleted(true);
            this.userRepository.save(user);
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot get user with id: " + id);
        }
    }
}
