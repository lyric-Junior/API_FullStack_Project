package com.host.server.model.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import java.time.LocalDateTime;

@Entity
@Table(name = "Produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter @Setter
    @Column(nullable = false)
    private String nome;

    @Getter @Setter
    @Column(nullable = false, length = 300)
    private String descricao;

    @Getter @Setter
    @Column(nullable = false)
    private BigDecimal valor;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dataDeCadastro;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime ultimaModificacao;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private PlanoDeNegocio plano;
}



