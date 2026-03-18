package com.host.server.service;

import com.host.server.model.DTO.ProdutoDTO;
import com.host.server.model.DTO.UsuarioDTO;
import com.host.server.model.Entitys.Produto;
import com.host.server.model.ServerSecurity.SecurityConfig;
import com.host.server.repository.ProdutoRepository;
import com.host.server.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository userRepo;

    private static final Pattern NOME_USUARIO_PATTERN = Pattern.compile("^[\\p{L} _-]+$");

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    private static final Pattern NOME_PRODUTO_PATTERN = Pattern.compile("^[A-Za-z0-9_#()]+$");

    private ProdutoDTO convertProductToDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();

        dto.setDescricao(produto.getDescricao());
        dto.setNome(produto.getNome());
        dto.setValor(produto.getValor());
        dto.setId(produto.getId());

        return dto;
    }

    public List<ProdutoDTO> listarProdutos(UsuarioDTO user) {
        List<Produto> produtos = produtoRepository.findAll();
        //verifica se o usuario é adm
        if (!user.isAdmin()) {
            throw new RuntimeException("O usuário não é um admin");
        }
        //repo validations
        else if (!userRepo.existsById(user.getId())) {
            throw new RuntimeException("O usuário não existe");
        }
        //Patterns
        else if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("O email do usuário é inválido");
        } else if (!NOME_USUARIO_PATTERN.matcher(user.getUserName()).matches()) {
            throw new IllegalArgumentException("O nome do usuario é inválido");
        }
        //Lengths
        else if (user.getUserName().length() > 20) {
            throw new IllegalArgumentException("O nome do usuário é muito grande. max = 20");
        }

        else if (user.getId() == null) {
            throw new RuntimeException("O usuário não possui um id ou não foi informado.");
        }

        return produtos.stream()
                .map(this::convertProductToDTO)
                .collect(Collectors.toList());
    }

    public void deletarProduto(Long id, UsuarioDTO user) {
        //Busca pelo produto
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("O produto selecionado não existe");
        }
        //User global Validation
        if (!user.isAdmin()) {
            throw new RuntimeException("O usuário não é um admin");
        }
        //repo validations
        else if (!userRepo.existsById(user.getId())) {
            throw new RuntimeException("O usuário não existe");
        }
        //Patterns
        else if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("O email do usuário é inválido");
        } else if (!NOME_USUARIO_PATTERN.matcher(user.getUserName()).matches()) {
            throw new IllegalArgumentException("O nome do usuario é inválido");
        }
        //Lengths
        else if (user.getUserName().length() > 20) {
            throw new IllegalArgumentException("O nome do usuário é muito grande. max = 20");
        }

        else if (user.getId() == null) {
            throw new RuntimeException("O usuário não possui um id ou não foi informado.");
        }


        //Patterns
        else if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new RuntimeException("O email do usuário é inválido");
        } else if (!NOME_USUARIO_PATTERN.matcher(user.getUserName()).matches()) {
            throw new RuntimeException("O nome do usuário é inválido");
        }
        produtoRepository.deleteById(id);
    }

    public Produto cadastrarProduto(Produto produtoDTO, UsuarioDTO user) {
        Produto novoProduto = new Produto();
        //Validações dentro do banco de dados
        if (!userRepo.existsById(user.getId())) {
            throw new IllegalArgumentException("o usuário não existe");
        } else if (!produtoRepository.existsById(produtoDTO.getId())) {
            throw new RuntimeException("O produto já existe");
        }
        //Validações de formatação de valores
        else if (EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new RuntimeException("O email do usuário é inválido");
        } else if (NOME_USUARIO_PATTERN.matcher(user.getUserName()).matches()) {
            throw new IllegalArgumentException("O nome do usuário é inválido");
        } else if (produtoDTO.getDescricao().length() > 300) {
            throw new IllegalArgumentException("A descrição do produto é muito grande");
        } else if (produtoDTO.getNome().matches("^[A-Z0-9]$")) {
            throw new RuntimeException("O nome de usuario é inválido");
        }

        novoProduto.setValor(produtoDTO.getValor());
        novoProduto.setDescricao(produtoDTO.getDescricao());
        novoProduto.setNome(produtoDTO.getNome().toUpperCase());
        novoProduto.setUltimaModificacao(LocalDateTime.now());

        return produtoRepository.save(novoProduto);
    }

    public void editarProduto(ProdutoDTO produto, Long id, UsuarioDTO user) {
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
