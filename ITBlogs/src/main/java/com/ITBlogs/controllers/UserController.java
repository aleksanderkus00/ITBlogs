package com.ITBlogs.controllers;

import com.ITBlogs.models.DTO.UserSettingsDto;
import com.ITBlogs.models.LoginModel;
import com.ITBlogs.models.RegisterModel;
import com.ITBlogs.models.User;
import com.ITBlogs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    long loginUser(@RequestBody LoginModel loginModel){
        try {
            var user = this.userRepository.findByEmail(loginModel.getEmail());
            byte[] saltedPassword = (loginModel.getPassword() + new String(user.getPasswordSalt())).getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            var passwordHash = md.digest(saltedPassword);
            if(Arrays.equals(passwordHash, user.getPasswordHash())) return user.getId();
            return 0;
        }
        catch (Exception ex){
            this.logger.error("Cannot login user " + ex.getMessage());
        }
        return 0;
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
                byte[] saltedPassword = (userSettings.getPassword() + new String(user.getPasswordSalt())).getBytes();
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                user.setPasswordHash(md.digest(saltedPassword));
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
