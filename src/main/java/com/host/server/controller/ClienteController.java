package com.host.server.controller;

import com.host.server.model.dto.ClienteDTO;
import com.host.server.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<String> cadastrarCliente(@RequestBody ClienteDTO cliente,
            Authentication auth) {

        String username = auth.getName();

        service.cadastrarCliente(cliente);

        return ResponseEntity.ok("Client registered successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCliente(@PathVariable Long id, Authentication auth) {
        String username = auth.getName();

        service.deletarCliente(id);

        return ResponseEntity.ok("The client was deleted successfully!");
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes(Authentication auth) {
        //Antes usavamos DTO's para validação e agora usaremos apenas o JWT.
        List<ClienteDTO> lista = service.listarClientes();

        return ResponseEntity.ok(lista);
    }

    @PutMapping
    public ResponseEntity<String> editarCliente(@RequestBody ClienteDTO cliente, Authentication auth) {
        service.editarCliente(cliente);

        return ResponseEntity.ok("Client " + cliente.getNome() + " updated successfully!");
    }
}