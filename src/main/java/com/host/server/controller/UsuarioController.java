package com.host.server.controller;

import com.host.server.model.entitys.Usuario;
import com.host.server.service.UsuarioService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario user) {
        service.cadastrarUsuario(user);
        return ResponseEntity.ok("Admin Registered, please, burn this code");
    }
}
