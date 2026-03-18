package com.host.server.model.DTO;


import com.host.server.model.Entitys.Cliente;
import com.host.server.model.Entitys.PlanoDeNegocio;
import com.host.server.model.Entitys.Produto;
import com.host.server.model.Entitys.Usuario;
import jakarta.persistence.OneToMany;

import java.util.List;

public class VendaDTO {

    private Long id;

    private Cliente cliente;

    private Usuario vendedor;

    private String descricao;

    private String tipoDeVenda;

    private List<ProdutoDTO> produtos;

    private PlanoDeNegocio plano;

    public Usuario getVendedor() {return vendedor;}
    public void setVendedor(Usuario vendedor1) {this.vendedor = vendedor1;}

    public PlanoDeNegocio getPlano() {return plano;}
    public void setPlano(PlanoDeNegocio plano) {this.plano = plano;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public String getTipoDeVenda() {return tipoDeVenda;}
    public void setTipoDeVenda(String tipoVenda) {this.tipoDeVenda = tipoVenda;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public List<ProdutoDTO> getProdutos() {return produtos;}
    public void setProdutos(List<ProdutoDTO> produtos) {this.produtos = produtos;}
}
