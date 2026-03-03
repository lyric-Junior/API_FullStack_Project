package com.host.server.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Vendas")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int vendaId;

    @OneToMany(cascade =  CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "venda")
    private List<Produto> produtos;

    @Column(nullable = false, length = 20)
    private String tipoDeVenda;

    @Column(nullable = false, length = 20)
    private String plano;

    @Column(length = 300, nullable = false)
    private String descricao;

    @Column(nullable = false, length = 50)
    private Usuario vendedor;

    @Column(nullable = false)
    private LocalDateTime dataVenda;

    public Venda() {}

    public int getVendaId() {return vendaId;}
    public void setVendaId(int vendaId) {this.vendaId = vendaId;}

    public LocalDateTime getDataVenda() {return dataVenda;}
    public void setDataVenda(LocalDateTime dataVenda) {this.dataVenda = dataVenda;}

    public List<Produto> getProdutos() {return produtos;}
    public void setProdutos(List<Produto> produtos) {this.produtos = produtos;}



}
