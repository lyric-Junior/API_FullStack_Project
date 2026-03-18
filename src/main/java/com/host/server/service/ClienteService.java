package com.host.server.service;

import com.host.server.model.DTO.ClienteDTO;
import com.host.server.model.DTO.ClienteLogin;
import com.host.server.model.DTO.UsuarioDTO;
import com.host.server.model.Entitys.Cliente;
import com.host.server.model.Entitys.Usuario;
import com.host.server.repository.ClienteRepository;
import com.host.server.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.host.server.service.PatternValidationService.*;

public class ClienteService {

    //Classes que serão utilizadas dentro do código
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository userRepo;

    @Autowired
    private PatternValidationService patternValidationService;

    //Funções privadas como o converter para DTO
    private ClienteDTO convertToClienteDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        return dto;
    }
    //Função opcional para futura refatoracao
    /*
    private UsuarioDTO convertUserDTO(Usuario usuario) {
        UsuarioDTO userDTO = new UsuarioDTO();

        userDTO.setUserName(usuario.getUserName());
        userDTO.setEmail(usuario.getEmail());
        userDTO.setId(usuario.getId());

        return userDTO;
    }*/

    //Lista de clientes
    public List<ClienteDTO> listarClientes(Usuario user) {
        // "!isAdmin"
        //verifica se o usuario é adm
        if (!user.isAdmin()) {
            throw new RuntimeException("O usuário não é um admin");
        }
        //repo validations
        else if (!userRepo.existsById(user.getId())) {
            throw new RuntimeException("O usuário não existe");
        }
        //Patterns
        else if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("O email do usuário é inválido");
        } else if (!NOME_USUARIO_PATTERN.matcher(user.getUserName()).matches()) {
            throw new IllegalArgumentException("O nome do usuario é inválido");
        }
        //Lengths
        else if (user.getUserName().length() > 20) {
            throw new IllegalArgumentException("O nome do usuário é muito grande. max = 20");
        }

        else if (user.getId() == null) {
            throw new RuntimeException("O usuário não possui um id ou não foi informado.");
        }

        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(this::convertToClienteDTO)
                .collect(Collectors.toList());
    }

    //Deletar cliente
    public void deletarCliente(ClienteDTO cliente, UsuarioDTO user) {
        //Client Validations
        if (cliente.getNome().length() > 100) {
            throw new RuntimeException("O nome do cliente é inválido. max = 100 caracteres");
        } else if(cliente.getCpf().length() > 11 ) {
            throw new RuntimeException("O CPF do cliente é inválido. max = 11 caracteres");
        } else if (!clienteRepository.existsById(cliente.getId())) {
            throw new RuntimeException("O cliente não existe ou não pode ser encontrado.");
        }
        //User validator
        patternValidationService.validateUser(user);

        clienteRepository.deleteById(cliente.getId());
    }
    //Função para editar o cliente
    public Cliente editarCliente(ClienteDTO cliente, UsuarioDTO user) {
        //Puxando o cliente ja existente
        Cliente clienteExistente = clienteRepository.findById(cliente.getId())
                .orElseThrow(() -> new RuntimeException("O id do cliente não é o correto ou inválido"));
        //Validations
        patternValidationService.validateCliente(cliente);
        patternValidationService.validateUser(user);

        clienteExistente.setNome(cliente.getNome().toUpperCase());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setUltimaEdicao(LocalDateTime.now());

        return clienteRepository.save(clienteExistente);
    }
    //Cadastrar cliente
    public Cliente cadastrarCliente(ClienteDTO cliente, UsuarioDTO user) {

        patternValidationService.validateCliente(cliente);
        patternValidationService.validateUser(user);

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
        novoCliente.setSenha(senhaHash);

        return clienteRepository.save(novoCliente);
    }


}
