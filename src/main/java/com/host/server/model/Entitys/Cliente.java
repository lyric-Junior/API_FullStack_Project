package com.host.server.model.Entitys;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Clientes")
public class Cliente {

    //Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 13)
    private String registroGeral;

    @Column(nullable = false)
    private boolean divida;

    @Column
    private LocalDateTime dataDeCadastro;

    @Column
    private LocalDateTime ultimaEdicao;

    public Cliente() {}

    public LocalDateTime getUltimaEdicao() {return ultimaEdicao;}
    public void setUltimaEdicao(LocalDateTime ultimaEdicao) {this.ultimaEdicao = ultimaEdicao;}

    public LocalDateTime getDataDeCadastro() {return dataDeCadastro;}
    public void setDataDeCadastro(LocalDateTime dataDeCadastro) {this.dataDeCadastro = dataDeCadastro;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

    public String getRegistroGeral() {return registroGeral;}
    public void setRegistroGeral(String registroGeral) {this.registroGeral = registroGeral;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public boolean getDivida() {return divida;}
    public void setDivida(boolean divida) {this.divida = divida;}

    public String getSenha() {return senha;}
    public void setSenha(String senha) {this.senha = senha;}
}