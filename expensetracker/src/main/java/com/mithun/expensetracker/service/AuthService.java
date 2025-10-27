package com.mithun.expensetracker.service;

import com.mithun.expensetracker.entity.LoginRequest;
import com.mithun.expensetracker.entity.LoginResponse;
import com.mithun.expensetracker.entity.User;
import com.mithun.expensetracker.repo.UserRepo;
import com.mithun.expensetracker.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;

    public String register(User user) {
        try{
            User userResponse = userRepo.findByUserName(user.getUsername()).orElse(null);
            if(userResponse != null){
                return "User Already REGISTERED";
            }

            userRepo.save(new User(user.getUserId(),user.getUsername(),passwordEncoder.encode(user.getPassword()),user.getEmail(),new BigDecimal(0)));
            emailService.sendMail(user.getEmail(),user.getUsername());
            return "User REGISTERED Successfully !!";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public LoginResponse login(LoginRequest request) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String token = jwtUtils.generateToken(user);
            return new LoginResponse(token, user.getUserId(), "200");
        } catch (Exception e) {
            return new LoginResponse(null,null,"User does not exist");
        }

    }
}
