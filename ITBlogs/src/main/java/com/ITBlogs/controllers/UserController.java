package com.ITBlogs.controllers;

import com.ITBlogs.models.User;
import com.ITBlogs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    User createUser(@RequestBody User user) {
        return this.userRepository.save(user);
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

    @PutMapping("/{id}")
    User updateUserById(@RequestBody User updatedUser, @PathVariable long id) {
        try {
            var user = this.userRepository.findById(id).get();
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            this.userRepository.save(user);
        } catch (NoSuchElementException exception){
            this.logger.warn("Cannot update user with id: " + id);
        }
        return null;
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
