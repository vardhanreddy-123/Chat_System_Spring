package com.project.auth_service.controller;


import com.project.auth_service.dto.LoginRequest;
import com.project.auth_service.model.User;
import com.project.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return authService.signup(user);
    }

    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request){

        return authService.login(request);
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication) {

        return "Logged in user: " + authentication.getName();
    }
}

