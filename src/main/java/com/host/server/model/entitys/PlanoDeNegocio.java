package com.host.server.model.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class PlanoDeNegocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter @Setter
    @Column(nullable = false)
    private String tipoDePlano;

    @Getter @Setter
    @Column(nullable = false)
    private String descricao;

    @Getter @Setter
    @Column(nullable = false)
    private BigDecimal valor;

    @Getter @Setter
    @OneToMany(mappedBy = "plano")
    private List<Produto> produtos;

    @Getter @Setter
    @OneToMany(mappedBy = "plano")
    private List<Venda> vendas;
}
