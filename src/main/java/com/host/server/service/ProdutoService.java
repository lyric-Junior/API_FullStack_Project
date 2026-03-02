package com.host.server.service;


import com.host.server.model.Cliente;
import com.host.server.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List <Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("O id do produto é inválido");
        }
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("A operação não pode ser concluída"));
    }
}
