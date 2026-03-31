package com.host.server.service;

import com.host.server.model.entitys.Usuario;
import com.host.server.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Usuario usuario = repository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getUserName())
                .password(usuario.getSenhaHash()) // 🔐 hash do banco
                .roles(usuario.isAdmin() ? "ADMIN" : "USER")
                .build();
    }
}