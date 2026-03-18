package com.host.server.model.DTO.loginAuth;


import org.springframework.stereotype.Component;

@Component
public class LoginRequest {
    private String username;

    private String passwordHash;

    public String getUsername() {return  username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return passwordHash;}
    public void setPassword(String password) {this.passwordHash = password;}
}
