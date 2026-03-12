package com.host.server.model.DTO;


import lombok.Getter;
import lombok.Setter;

public class ClienteLogin {

    private String login;

    private String senhaHash;

    public String getLogin() {return login;}
    public void setLogin(String login) {this.login = login;}

    public String getSenha() {return senhaHash;}
    public void setSenha(String senha) {this.senhaHash = senha;}
}
