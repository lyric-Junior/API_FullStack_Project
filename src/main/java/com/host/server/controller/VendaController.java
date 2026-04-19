package com.host.server.controller;


import com.host.server.model.dto.VendaDTO;
import com.host.server.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping("/listarVendas")
    public ResponseEntity<List<VendaDTO>> listarVendas() {
        vendaService.list
    }

}
