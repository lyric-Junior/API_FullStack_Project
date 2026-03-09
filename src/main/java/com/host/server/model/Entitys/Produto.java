package com.host.server.model.Entitys;


import com.host.server.model.DTO.ProdutoDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 300)
    private String descricao;

    @Column(nullable = false, length = 11)
    private BigDecimal valor;

    @Column(nullable = false, length = 13)
    private LocalDateTime dataDeCadastro;

    @Column(nullable = false)
    private Date ultimaModificacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produtos_id")
    private Venda venda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produtos_id")
    private PlanoDeNegocio plano;

    //String
    public Venda getVenda() {return venda;}
    public void setVenda(Venda venda) {this.venda = venda;}

    public Long getId() {return id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public BigDecimal getValor() {return valor;}
    public void setValor(BigDecimal valor) {this.valor = valor;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public LocalDateTime getDataDeCadastro() {return dataDeCadastro;}
    public void setDataDeCadastro(LocalDateTime dataDeCadastro) {this.dataDeCadastro = dataDeCadastro;}

    public Date getUltimaModificacao() {return ultimaModificacao;}
    public void setUltimaModificacao(Date ultimaModificacao) {this.ultimaModificacao = ultimaModificacao;}

    public PlanoDeNegocio getProdutosPlano() {return plano;}
    public void setProdutosPlano(PlanoDeNegocio plano) {this.plano = plano;}

}



