package com.host.server.model.Entitys;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String userName;

    @Column(unique = true, nullable = true)
    public boolean admin;

    @Column(nullable = false)
    private String senhaHash;

    @Column(nullable = false)
    private LocalDateTime dataDeCadastro;


    public LocalDateTime getDataDeCadastro() {return dataDeCadastro;}
    public void setDataDeCadastro(LocalDateTime dataDeCadastro) {this.dataDeCadastro = LocalDateTime.now();}

    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}

    public String getPassword() {return senhaHash;}
    public void setPassword(String password) {this.senhaHash = password;}

    public Long getId() {return id;}
    protected void setId(Long id) {this.id = id;}

    public boolean getAdmin() {return admin;}
    protected void setAdmin(boolean admin) {this.admin = admin;}
}
