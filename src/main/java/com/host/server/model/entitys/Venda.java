package com.host.server.model.entitys;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Vendas")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendaItem> itens = new ArrayList<>();

    @Getter @Setter
    @Column(nullable = false)
    @Max(20)
    @Min(5)
    private String tipoDeVenda;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id")
    @Getter @Setter
    private PlanoDeNegocio plano;

    @Getter @Setter
    @Column()
    @Max(300)
    @NotNull
    private String descricao;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dataVenda;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
