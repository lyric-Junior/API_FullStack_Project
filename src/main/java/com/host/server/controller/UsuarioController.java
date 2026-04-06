package com.host.server.controller;

import com.host.server.model.dto.UsuarioDTO;
import com.host.server.model.entitys.Usuario;
import com.host.server.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario user, Authentication  auth) {
        service.cadastrarUsuario(user);
        return ResponseEntity.ok("User " + user.getUserName() + " registered successfully!");
    }

    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(Authentication auth) {
        List<UsuarioDTO> usuarios = service.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/deletarUsuario/{id}")
    public ResponseEntity<String> deletarUsuario(@RequestParam Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.ok("User deleted successfully!");
    }
}
