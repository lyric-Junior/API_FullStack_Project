package com.host.server.service;

import com.host.server.model.dto.ProdutoDTO;
import com.host.server.model.entitys.Produto;
import com.host.server.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.host.server.service.ValidationService.NOME_USUARIO_PATTERN;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ValidationService validationService;

    private ProdutoDTO convertProductToDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();

        dto.setDescricao(produto.getDescricao());
        dto.setNome(produto.getNome());
        dto.setValor(produto.getValor());
        dto.setId(produto.getId());

        return dto;
    }

    public List<ProdutoDTO> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(this::convertProductToDTO)
                .collect(Collectors.toList());
    }

    public void deletarProduto(Long id) {
        //Busca pelo produto
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("O produto selecionado não existe");
        }

        produtoRepository.deleteById(id);
    }

    public Produto cadastrarProduto(ProdutoDTO produtoDTO) {
        Produto novoProduto = new Produto();

        if (produtoRepository.existsById(produtoDTO.getId())) {
            throw new RuntimeException("O produto já existe");
        } else if (produtoDTO.getDescricao().length() > 300) {
            throw new IllegalArgumentException("A descrição do produto é muito grande");
        }

        novoProduto.setValor(produtoDTO.getValor());
        novoProduto.setDescricao(produtoDTO.getDescricao());
        novoProduto.setNome(produtoDTO.getNome().toUpperCase());
        novoProduto.setUltimaModificacao(LocalDateTime.now());

        return produtoRepository.save(novoProduto);
    }

    public void editarProduto(ProdutoDTO produto, Long id) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("O produto não pode ser encontrado"));
        if (produto.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é inválido");
        } else if (produto.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("O campo de descrição é obrigatório");
        } else if(!NOME_USUARIO_PATTERN.matcher(produto.getNome()).matches()) {
            throw new IllegalArgumentException("O nome de usuário é inválido");
        }

        produtoExistente.setNome(produto.getNome().toUpperCase());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setValor(produto.getValor());
        produtoExistente.setUltimaModificacao(LocalDateTime.now());

        produtoRepository.save(produtoExistente);
    }

}
