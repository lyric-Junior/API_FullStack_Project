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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClienteService {

    //Classes que serão utilizadas dentro do código
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository userRepo;

    private static final Pattern NOME_PATTERN = Pattern.compile("^[\\p{L} _-]+$");

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

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
        if (!user.getAdmin()) {
            throw new IllegalArgumentException("O usuário não é administrador");
        }
        //User Repo Validation
        else if (userRepo.existsById(user.getId())) {
            throw new RuntimeException("O usuário não existe ou não foi encontrado");
        }
        //Lengths
        else if (user.getUserName().length() > 20) {
            throw new IllegalArgumentException("O nome do usuário é muito grande. max = 20");
        } else if (user.getEmail().length() > 255) {
            throw new IllegalArgumentException("O email é inválido ou não pode ser utilizado");
        }

        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(this::convertToClienteDTO)
                .collect(Collectors.toList());
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
        //Puxando o cliente ja existente
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("O id do cliente não é o correto ou inválido"));
        //Validações
        if (cliente.getNome().length() > 100) {
            throw new IllegalArgumentException("O nome do cliente é inválido. max = 100 caracteres");
        } else if (cliente.getEmail().matches("^[A-Za-z0-9+_.-]$"))

        clienteExistente.setNome(cliente.getNome().toUpperCase());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setUltimaEdicao(LocalDateTime.now());

        return clienteExistente;
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
        }
        else if (cliente.getEmail() == null || !cliente.getEmail()
                .matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("O email inserido é inválido.");
        }
        else if(!user.isAdmin()){
            throw new IllegalAccessError("O usuario não é adm.");
        }

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
