package com.host.server.model.dto;


public class ClienteLogin {

    private String login;

    private String senhaHash;

    public String getLogin() {return login;}
    public void setLogin(String login) {this.login = login;}

    public String getSenha() {return senhaHash;}
    public void setSenha(String senha) {this.senhaHash = senha;}
}
