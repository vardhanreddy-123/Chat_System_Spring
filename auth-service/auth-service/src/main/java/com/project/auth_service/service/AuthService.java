package com.project.auth_service.service;

import com.project.auth_service.dto.LoginRequest;
import com.project.auth_service.model.User;
import com.project.auth_service.repository.UserRepository;
import com.project.auth_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signup(User user) {

        String hashedPassword =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }


    @Autowired
    private JwtUtil jwtUtil;

    public String login(LoginRequest request){

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        boolean matched =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if(!matched){
            throw new RuntimeException(
                    "Invalid password"
            );
        }

        return jwtUtil.generateToken(
                user.getEmail()
        );
    }


}