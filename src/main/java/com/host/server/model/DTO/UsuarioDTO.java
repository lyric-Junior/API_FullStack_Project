package com.host.server.model.DTO;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class UsuarioDTO {


    private Long id;

    private String email;

    private String userName;

    private LocalDateTime dataDeCadastro;

    private boolean admin;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}

    public boolean isAdmin() {return admin;}
    public void setAdmin(boolean admin) {this.admin = admin;}
}
