package com.host.server.model.DTO.loginAuth;



public class LoginResponse {

    private String token;

    private String type = "Bearer";

    private Long id;

    private String userName;

    private String email;

    private boolean admin;

    public LoginResponse() {}

    //Getters and Setters
    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getType() {return type;}
    public void setType(String type1) {this.type = type1;}

    public String getUserName() {return userName;}
    public void setUserName(String userName1) {this.userName = userName1;}

    public String getEmail() {return email;}
    public void setEmail(String email1) {this.email = email1;}

    public boolean isAdmin() {return admin;}
    public void setAdmin(boolean admin1) {this.admin = admin1;}
}
