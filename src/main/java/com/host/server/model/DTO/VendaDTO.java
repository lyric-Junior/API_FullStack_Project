package com.host.server.model.DTO;


import com.host.server.model.Entitys.Cliente;
import com.host.server.model.Entitys.Produto;
import jakarta.persistence.OneToMany;

import java.util.List;

public class VendaDTO {

    private Long id;

    private Cliente cliente;

    private List<ProdutoDTO> produtos;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public List<ProdutoDTO> getProdutos() {return produtos;}
    public void setProdutos(List<ProdutoDTO> produtos) {this.produtos = produtos;}
}
