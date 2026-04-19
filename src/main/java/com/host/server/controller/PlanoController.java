package com.host.server.controller;

import com.host.server.model.entitys.PlanoDeNegocio;
import com.host.server.service.PlanoDeNegocioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanoController {

    @Autowired
    private PlanoDeNegocioService service;

    @GetMapping("/listarPlanos")
    public ResponseEntity<List<PlanoDeNegocio>> listarPlanos() {
        return ResponseEntity.ok(service.listarPlanos());
    }

    @PostMapping("/cadastrarPlano")
    public ResponseEntity<String> cadastrarPlano(Authentication auth,@Valid @RequestBody PlanoDeNegocio plano) {
        service.cadastrarPlanoDeNegocio(plano);
        return ResponseEntity.ok("The Business plan " + plano.getTipoDePlano() + " was registered successfully");
    }

    @DeleteMapping("/deletarPlano")
    public ResponseEntity<String> deletarProduto(Authentication auth, @RequestParam Long id) {
        service.deletarPlano(id);
        return ResponseEntity.ok("The plan was successfully deleted!");
    }
}

