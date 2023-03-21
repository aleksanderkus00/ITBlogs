package com.ITBlogs.controllers;

import com.ITBlogs.models.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping()
    User createUser(@RequestBody User user) {
        return null;
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable long id) {
        return null;
    }

    @PutMapping("/{id}")
    User updateUserById(@RequestBody User user, @PathVariable long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable long id) {

    }
}
