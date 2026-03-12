package com.host.server.model.DTO;


import lombok.Getter;
import lombok.Setter;

public class ClienteDTO {


    private Long id;

    private String nome;

    private String email;

    private String RG;

    private String cpf;

    private boolean divida;

    private String senha;

    public ClienteDTO() {};

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getRG() {return RG;}
    public void setRG(String RG) {this.RG = RG;}

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

    public boolean isDivida() {return divida;}
    public void setDivida(boolean divida) {this.divida = divida;}

    public String getSenha() {return senha;}
    public void setSenha(String senhaHash) {this.senha = senhaHash;}
}
