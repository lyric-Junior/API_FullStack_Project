package com.host.server.service;


import com.host.server.model.DTO.UsuarioDTO;
import com.host.server.model.Entitys.Usuario;
import com.host.server.model.ServerSecurity.SecurityConfig;
import com.host.server.repository.ProdutoRepository;
import com.host.server.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private SecurityConfig securityConfig;

    private PatternValidationService patternValidationService;

    public String cadastrarUsuario(Usuario user, UsuarioDTO dto) {
        Usuario novoUsuario = new Usuario();

        patternValidationService.validateUser(dto);

        String senhaHash = securityConfig.passwordEncoder().encode(user.getPassword());
        String cpfHash = securityConfig.passwordEncoder().encode(user.getCpfHash());

        novoUsuario.setPassword(senhaHash);
        novoUsuario.setCpfHash(cpfHash);
        novoUsuario.setEmail(user.getEmail());
        novoUsuario.setUserName(user.getUserName());
        novoUsuario.setDataDeCadastro(LocalDateTime.now());

    }
}
