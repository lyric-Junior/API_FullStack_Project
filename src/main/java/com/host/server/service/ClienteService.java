package com.host.server.service;

import com.host.server.model.DTO.ClienteDTO;
import com.host.server.model.DTO.ClienteLogin;
import com.host.server.model.DTO.UsuarioDTO;
import com.host.server.model.Entitys.Cliente;
import com.host.server.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Lista de clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
    //Deletar cliente
    public void deletarCliente(Cliente cliente, Long id) {
        if (cliente.getNome().length() > 100) {
            throw new RuntimeException("O nome do cliente é inválido. max = 100 caracteres");
        } else if(cliente.getCpf().length() > 11 ) {
            throw new RuntimeException("O CPF do cliente é inválido. max = 11 caracteres");
        } else if(cliente.getRegistroGeral().length() > 13) {
            throw new IllegalArgumentException("O RG do cliente é inválido. max = 13 caracteres");
        } else if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("O cliente não existe ou não pode ser encontrado.");
        }
        clienteRepository.deleteById(id);
    }
    //Função para editar o cliente
    public Cliente editarCliente(ClienteDTO cliente, Long id) {
        Cliente clienteNovo = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("O id do cliente não é o correto ou inválido"));
        if (cliente.getNome().length() > 100) {
            throw new IllegalArgumentException("O nome do cliente é inválido. max = 100 caracteres");
        }
        clienteNovo.setNome(cliente.getNome());
        clienteNovo.setEmail(cliente.getEmail());

        return clienteNovo;
    }
    //Cadastrar cliente
    public Cliente cadastrarCliente(ClienteDTO cliente, UsuarioDTO user) {
        if(cliente.getNome().length() > 100) {
            throw new IllegalArgumentException("O nome do cliente é inválido. max = 100 caracteres");
        } else if(cliente.getCpf().length() > 11 ) {
            throw new IllegalArgumentException("O CPF do cliente é inválido. max = 11 caracteres ");
        } else if(clienteRepository.existsById(cliente.getId())) {
            throw new RuntimeException("O cliente já está cadastrado dentro do sistema");
        } else if (cliente.getSenha().length() < 8) {
            throw new CompromisedPasswordException("A senha do cliente não é válida. min = 8");
        } else if (cliente.getEmail() == null || !cliente.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            throw new IllegalArgumentException("O email inserido é inválido.");
        } else if(!user.isAdmin()){
            throw new IllegalAccessError("O usuario não é adm.");
        }

        Cliente novoCliente = new Cliente();

        //Hash para dados importantes
        String rgHash = passwordEncoder.encode(cliente.getRG());
        String cpfHash = passwordEncoder.encode(cliente.getCpf());
        String senhaHash = passwordEncoder.encode(cliente.getSenha());


        novoCliente.setNome(cliente.getNome());
        novoCliente.setEmail(cliente.getEmail());
        novoCliente.setCpf(cpfHash);
        novoCliente.setRegistroGeral(rgHash);
        novoCliente.setSenha(senhaHash);

        return clienteRepository.save(novoCliente);
    }
}
