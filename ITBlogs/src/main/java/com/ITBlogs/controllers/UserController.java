package com.ITBlogs.controllers;

import com.ITBlogs.models.LoginModel;
import com.ITBlogs.models.RegisterModel;
import com.ITBlogs.models.User;
import com.ITBlogs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    boolean registerUser(@RequestBody RegisterModel registerModel) {
        try {
            var user = new User(
                    registerModel.getUsername(),
                    registerModel.getEmail(),
                    registerModel.getPassword());
            this.userRepository.save(user);
            return true;
        }catch (DataIntegrityViolationException ex) {
            this.logger.error("User with this email already exists");
        } catch (Exception ex) {
            this.logger.error("Cannot crete user " + ex.getMessage());
        }
        return false;
    }

    @PostMapping("/login")
    boolean loginUser(@RequestBody LoginModel loginModel){
        try {
            var user = this.userRepository.findByEmail(loginModel.getEmail());
            byte[] saltedPassword = (loginModel.getPassword() + new String(user.getPasswordSalt())).getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            var passwordHash = md.digest(saltedPassword);
            return Arrays.equals(passwordHash, user.getPasswordHash());
        }
        catch (Exception ex){
            this.logger.error("Cannot login user " + ex.getMessage());
        }
        return false;
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
