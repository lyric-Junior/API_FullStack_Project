package com.host.server.controller;

import com.host.server.model.entitys.PlanoDeNegocio;
import com.host.server.service.PlanoDeNegocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
