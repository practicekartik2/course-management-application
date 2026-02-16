package com.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apigateway.dto.AuthRequest;
import com.apigateway.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request){
        if("admin".equals(request.getUsername())&& "admin123".equals(request.getPassword())){
            return jwtUtil.generateToken(request.getUsername());
        }

        throw new RuntimeException("Invalid username or password");
    }
}
