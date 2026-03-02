package com.host.server.controller;


import com.host.server.model.Cliente;
import com.host.server.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/OryonSystem")
public class ClienteController {
    public ClienteService clienteService;

    @GetMapping("/listarClientes")
        public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> usuarios = clienteService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/deletarCliente")
    public ResponseEntity<Void> deletarCliente(@RequestParam Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/salvarCliente")
    public ResponseEntity<String> salvarCliente(@RequestParam Model model, Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usário criado com sucesso");
    }
    @PostMapping("/editarCliente/{id}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.searchById(id);
        return ResponseEntity.ok(cliente);
    }
}