package com.host.server.model.Entitys;

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
    private List<Produto> produtos;

    public PlanoDeNegocio() {}

    public String getTipoDePlano() {return tipoDePlano;}
    public void setTipoDePlano(String plano) {this.tipoDePlano = plano;}

    public Long getId() {return id;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public BigDecimal getValor() {return valor;}
    public void setValor(BigDecimal valor) {this.valor = valor;}

    public List<Produto> getProdutos() {return produtos;}
    public void setProdutos(List<Produto> produtos) {this.produtos = produtos;}
}
