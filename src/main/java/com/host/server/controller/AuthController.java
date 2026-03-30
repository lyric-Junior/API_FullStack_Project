package com.host.server.controller;

import com.host.server.model.dto.loginauth.LoginRequest;
import com.host.server.model.dto.loginauth.LoginResponse;
import com.host.server.model.dto.loginauth.ErrorLoginResponse;

import com.host.server.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            String token = jwtService.generateToken(request.getUsername());

            // 3. monta resposta
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUserName(request.getUsername());

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            // 🚨 resposta de erro padronizada
            ErrorLoginResponse error = new ErrorLoginResponse();
            error.setMessage("Invalid username or password");
            error.setStatus(401);
            error.setTimeStamp(new Date().getTime());

            return ResponseEntity.status(401).body(error);
        }
    }
}