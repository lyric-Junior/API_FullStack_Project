package com.host.server.service;


import com.host.server.model.dto.UsuarioDTO;
import com.host.server.model.entitys.Usuario;
import com.host.server.model.serversecurity.SecurityConfig;
import com.host.server.repository.ProdutoRepository;
import com.host.server.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecurityConfig securityConfig;

    private ValidationService validationService;

    private UsuarioDTO convertUserToDTO(Usuario user) {
        UsuarioDTO dto = new UsuarioDTO();

        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setAdmin(user.isAdmin());
        return dto;
    }

    @Transactional
    public String cadastrarUsuario(Usuario user) {
        Usuario novoUsuario = new Usuario();

        String senhaHash = securityConfig.passwordEncoder().encode(user.getSenhaHash());
        String cpfHash = securityConfig.passwordEncoder().encode(user.getCpfHash());

        novoUsuario.setSenhaHash(senhaHash);
        novoUsuario.setCpfHash(cpfHash);
        novoUsuario.setEmail(user.getEmail());
        novoUsuario.setUserName(user.getUserName());
        novoUsuario.setDataDeCadastro(LocalDateTime.now());

        return ("Cliente " + user.getUserName() + "cadastrado com sucesso!");
    }

    public void editarUsuario(Usuario user) {
        Usuario user1 = usuarioRepository.findById(user.getId())
                .orElseThrow(()-> new EntityNotFoundException("The user couldn't be found!"));

        user1.setUserName(user.getUserName());
        user1.setEmail(user.getEmail());
        user1.setAdmin(user.isAdmin());

        usuarioRepository.save(user1);
    }

    public void deletarCliente(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioDTO> listarClientes() {
        return usuarioRepository.findAll().stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }
}
