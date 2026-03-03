package com.host.server.model;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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



    //String

    public String getId() {return nome;}

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
}
