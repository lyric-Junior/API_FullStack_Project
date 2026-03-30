package com.host.server.controller;

import com.host.server.model.dto.ProdutoDTO;
import com.host.server.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos(Authentication auth) {
        return ResponseEntity.ok(service.listarProdutos());
    }

    @PostMapping
    public ResponseEntity<String> cadastrarProduto(@RequestBody ProdutoDTO dto, Authentication auth) {
        service.cadastrarProduto(dto);
        return ResponseEntity.ok("Client " + auth.getName() + "registered successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProduto(Authentication auth, @PathVariable Long id) {
        service.deletarProduto(id);
        return ResponseEntity.ok("The client was successfully deleted!");
    }

    @PutMapping("/editarProduto/{id}")
    public ResponseEntity<String> editarProduto(@RequestBody ProdutoDTO produto, @PathVariable Long id, Authentication auth) {
        service.editarProduto(produto, id);
        return ResponseEntity.ok("The client was successfully deleted!");
    }
}
