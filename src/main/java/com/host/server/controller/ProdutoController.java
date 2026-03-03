package com.host.server.controller;


import com.host.server.model.Cliente;
import com.host.server.model.Produto;
import com.host.server.service.ClienteService;
import com.host.server.service.ProdutoService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oryonsystem")
public class ProdutoController {

    public ProdutoService produtoService;

    @GetMapping("/listarProdutos")
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping("/salvarProduto")
    public ResponseEntity<String> listarProdutos(@RequestBody Produto produto) {
        Produto novoUsuario = produtoService.salvarProduto(produto);
        return ResponseEntity.ok("Cliente salvo com sucesso!" + produto.getNome());
    }

    @DeleteMapping("/deletarProduto{id}")
    public ResponseEntity <String> deletarProduto(@RequestParam Long id) {
        produtoService.deleteById(id);
        return ResponseEntity.ok("Produto excluído com sucesso");
    }

    @PutMapping("/editarProduto/{id}")
    public ResponseEntity<String> editarProduto(@RequestBody Produto produto, @RequestParam Long id) {
        Produto existente = produtoService.findById(id);
        Produto novo = produtoService.salvarProduto(produto);
        return ResponseEntity.ok("Produto" + existente.getId() + "EDITADO com sucesso!");
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<Produto> produtoResponseEntity(@RequestParam Long id) {
        Produto item = produtoService.findById(id);
        return ResponseEntity.ok(item);
    }

}
