package com.host.server.model.Entitys;


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
    private PlanoDeNegocio plano;

    @Column(length = 300)
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

    public Usuario getVendedor() {return vendedor;}
    public void setVendedor(Usuario vendedor) {this.vendedor = vendedor;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public String getTipoDeVenda() {return tipoDeVenda;}
    public void setTipoDeVenda(String tipoDeVenda) {this.tipoDeVenda = tipoDeVenda;}

    public PlanoDeNegocio getPlano() {return plano;}
    public void setPlano(PlanoDeNegocio plano) {this.plano = plano;}

    public void atualizarProduto(List<Produto> novoProdutos) {
        this.produtos.clear();
        this.produtos.addAll(novoProdutos);
    }

}
