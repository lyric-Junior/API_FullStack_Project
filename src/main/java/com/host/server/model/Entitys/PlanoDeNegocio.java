package com.host.server.model.Entitys;


import com.host.server.model.DTO.ProdutoDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class PlanoDeNegocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String tipoDePlano;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private BigDecimal valor;
    @OneToMany(mappedBy = "plano")
    @Column(nullable = false)
    private List<ProdutoDTO> produtos;
}
