package com.host.server.model.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Clientes")
public class Cliente {

    //Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter @Setter
    @Column(nullable = false, length = 100)
    private String nome;

    @Getter @Setter
    @Column(nullable = false)
    private String senhaHash;

    @Getter @Setter
    @Column(nullable = false)
    private String email;

    @Getter @Setter
    @Column(nullable = false, length = 11)
    private String cpf;

    @Getter @Setter
    @Column(nullable = false, length = 13)
    private String registroGeral;

    @Getter @Setter
    @OneToMany(mappedBy = "cliente")
    private List<Venda> vendas;

    @Getter @Setter
    @Column(nullable = false)
    private boolean divida;

    @Getter @Setter
    @Column
    private LocalDateTime dataDeCadastro;

    @Getter @Setter
    @Column
    private LocalDateTime ultimaEdicao;

}