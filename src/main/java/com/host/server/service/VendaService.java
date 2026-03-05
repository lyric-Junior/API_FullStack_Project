package com.host.server.service;


import com.host.server.model.Entitys.Venda;
import com.host.server.repository.ProdutoRepository;
import com.host.server.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaService {
    @Autowired
    private VendaRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Venda realizarVenda(Venda venda) {
        if (venda.get)

        Venda novaVenda = repository.save(venda);
        return novaVenda;
    }
}
