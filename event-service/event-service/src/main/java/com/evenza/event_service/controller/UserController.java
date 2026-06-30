package com.evenza.event_service.controller;

import com.evenza.event_service.model.User;
import com.evenza.event_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/users")
public class UserController {

@Autowired

private UserService userService;

@PostMapping("/register")
public User register (@RequestBody User user){
return userService.registerUser(user);
}

@PostMapping("/login")
public User login (@RequestBody User user){
    return  userService.loginUser(user.getEmail(), user.getPassword());

}
}
