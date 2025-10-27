package com.mithun.expensetracker.controller;

import com.mithun.expensetracker.entity.LoginRequest;
import com.mithun.expensetracker.entity.LoginResponse;
import com.mithun.expensetracker.entity.User;
import com.mithun.expensetracker.service.AuthService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/v1")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody User user){
        return new ResponseEntity<>(authService.register(user),HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
       return new ResponseEntity<>(authService.login(request),HttpStatus.OK);
    }


}
