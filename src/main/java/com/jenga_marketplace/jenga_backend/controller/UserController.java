package com.jenga_marketplace.jenga_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jenga_marketplace.jenga_backend.model.User;
import com.jenga_marketplace.jenga_backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

   @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
    User savedUser = userService.createUser(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
}

}
