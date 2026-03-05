package com.host.server.service;


import com.host.server.model.Entitys.Cliente;
import com.host.server.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente searchById(Long id) {
        if(id == null || id < 0) {
            throw new IllegalArgumentException("Desista dos seus sonhos e morra!");
        }
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }


    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        if (cliente.getCpf() > 11) {
            throw new IllegalArgumentException("O seu CPF é inválido, digite somente os números");
        } else if (cliente.getEmail().length() > 255) {
            throw new IllegalArgumentException("Seu email é muito grande e não pode ser utilizado");
        } else if (clienteRepository.findAll().isEmpty()) {
            throw new IllegalCallerException("Falha na conexão com os nossos servidores");
        } else if (cliente.getNome().length() > 255) {
            throw new IllegalArgumentException("Seu nome é muito grande e não poderá ser utilzado");
        }

        return clienteRepository.save(cliente);
    }


    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Não existem nenhum cadastro com esse Id");
        }

        clienteRepository.deleteById(id);
    }
}