package com.host.server.service;



import com.host.server.model.Entitys.Produto;
import com.host.server.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    public List <Produto> listarProdutos() {
        return repository.findAll();
    }

    public Produto findById(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("O id do produto é inválido");
        }
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("A operação não pode ser concluída"));
    }

    public Produto salvarProduto(Produto produto) {
        if (produto.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição do produto não pode estar vazia");
        } else if (produto.getDataDeCadastro().isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("O produto é inválido");        }
        return repository.save(produto);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Produto não existe ou não foi encontrado");
        }
        repository.deleteById(id);
    }

}
