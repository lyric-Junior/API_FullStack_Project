package com.host.server.service;

import com.host.server.model.DTO.ProdutoDTO;
import com.host.server.model.Entitys.Produto;
import com.host.server.model.ServerSecurity.SecurityConfig;
import com.host.server.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private SecurityConfig securityConfig;

    private static final Pattern NOME_PATTERN = Pattern.compile("^[\\p{L} _-]+$");

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("O produto selecionado não existe");
        }
        produtoRepository.deleteById(id);
    }

    public Produto cadastrarProduto(Produto produtoDTO, boolean admin) {
        Produto novoProduto = new Produto();

        novoProduto.setValor(produtoDTO.getValor());
        novoProduto.setDescricao(produtoDTO.getDescricao());
        novoProduto.setNome(produtoDTO.getNome());

        return produtoRepository.save(novoProduto);
    }

    public void editarProduto(ProdutoDTO produto, Long id, boolean admin) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("O produto não pode ser encontrado"));
        if (produto.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é inválido");
        } else if (produto.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("O campo de descrição é obrigatório");
        } else if(!NOME_PATTERN.matcher(produto.getNome()).matches()) {
            throw new IllegalArgumentException("O nome de usuário é inválido");
        }

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setValor(produto.getValor());


        produtoRepository.save(produtoExistente);
    }

}
