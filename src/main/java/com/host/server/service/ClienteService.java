package com.host.server.service;

import com.host.server.model.dto.ClienteDTO;
import com.host.server.model.dto.UsuarioDTO;
import com.host.server.model.entitys.Cliente;
import com.host.server.model.entitys.Usuario;
import com.host.server.repository.ClienteRepository;
import com.host.server.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    //Classes que serão utilizadas dentro do código
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UsuarioRepository userRepo;

    //Funções privadas como o converter para DTO
    private ClienteDTO convertToClienteDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        return dto;
    }

    private UsuarioDTO convertUserDTO(Usuario usuario) {
        UsuarioDTO userDTO = new UsuarioDTO();

        userDTO.setUserName(usuario.getUserName());
        userDTO.setEmail(usuario.getEmail());
        userDTO.setId(usuario.getId());

        return userDTO;
    }

    //Lista de clientes
    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(this::convertToClienteDTO)
                .collect(Collectors.toList());
    }

    //Deletar cliente
    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    //Função para editar o cliente
    public Cliente editarCliente(ClienteDTO cliente) {
        //Puxando o cliente ja existente
        Cliente clienteExistente = clienteRepository.findById(cliente.getId())
                .orElseThrow(() -> new RuntimeException("O id do cliente não é o correto ou inválido"));
        //Validations
        validationService.validateCliente(cliente);

        clienteExistente.setNome(cliente.getNome().toUpperCase());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setUltimaEdicao(LocalDateTime.now());

        return clienteRepository.save(clienteExistente);
    }
    //Cadastrar cliente
    public Cliente cadastrarCliente(ClienteDTO cliente) {

        validationService.validateCliente(cliente);

        Cliente novoCliente = new Cliente();

        //Hash para dados importantes
        String rgHash = passwordEncoder.encode(cliente.getRG());
        String cpfHash = passwordEncoder.encode(cliente.getCpf());
        String senhaHash = passwordEncoder.encode(cliente.getSenha());

        //Don't forget do uppercase names
        novoCliente.setNome(cliente.getNome().toUpperCase());
        novoCliente.setEmail(cliente.getEmail());
        novoCliente.setCpf(cpfHash);
        novoCliente.setRegistroGeral(rgHash);
        novoCliente.setSenhaHash(senhaHash);

        return clienteRepository.save(novoCliente);
    }
}
