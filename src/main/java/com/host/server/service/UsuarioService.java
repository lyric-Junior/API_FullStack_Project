package com.host.server.service;


import com.host.server.model.Entitys.Usuario;
import com.host.server.model.ServerSecurity.SecurityConfig;
import com.host.server.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository userRepo;

    @Autowired
    private SecurityConfig securityConfig;

    public Usuario loginAuth(String name, String password) {
        Usuario usuario = userRepo.listByUsername(name)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!securityConfig.passwordEncoder().matches(password, usuario.getPassword())) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        return userRepo.findAll();
    }

    public Optional<Usuario> encontrarPorNome(String nome) {
        return userRepo.listByUsername(nome);
    }

    public void deletar(Usuario usuario) {
        userRepo.delete(usuario);
    }

    public void editarUsuario(Usuario usuario, Long id) {
        Usuario user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não foi encontrado"));

        user.setUserName(usuario.getUserName());
    }

    public Usuario cadastrar(Usuario usuario, String nome, String password) {

        usuario.setUserName(nome);

        String senhaHash = securityConfig.passwordEncoder().encode(password);
        usuario.setPassword(senhaHash);

        return userRepo.save(usuario);
    }

}
