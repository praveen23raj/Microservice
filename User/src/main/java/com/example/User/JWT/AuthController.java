package com.example.User.JWT;

import com.example.User.dto.LoginDto;
import com.example.User.dto.TokenResponse;
import com.example.User.entity.User;
import com.example.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


        @Autowired
        private UserService userService;




    @GetMapping("/profile")
    public User getCurrentUser(@AuthenticationPrincipal User user){
        return user;
    }

}