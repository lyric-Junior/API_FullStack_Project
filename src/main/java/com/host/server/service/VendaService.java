package com.host.server.service;

import com.host.server.model.DTO.ProdutoDTO;
import com.host.server.model.DTO.UsuarioDTO;
import com.host.server.model.DTO.VendaDTO;
import com.host.server.model.Entitys.Produto;
import com.host.server.model.Entitys.Usuario;
import com.host.server.model.Entitys.Venda;
import com.host.server.repository.ProdutoRepository;
import com.host.server.repository.UsuarioRepository;
import com.host.server.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VendaService {

    @Autowired
    private VendaRepository vendaRepo;

    @Autowired
    private UsuarioRepository userRepo;

    @Autowired
    private ProdutoRepository produtoRepo;

    private static final Pattern EMAIL_USUARIO_PATTERN = Pattern
            .compile("^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$");
    private static final Pattern NOME_USUARIO_PATTERN = Pattern
            .compile("^[A-Z0-9]+(?:-[A-Z0-9]+)*$");

    public void adicionarItem(Venda venda, Usuario user, Produto produto) {
        Produto produto1 = produtoRepo.findById(produto.getId())
                .orElseThrow(() -> new RuntimeException("O produto não pode ser encontrado"));

        if (venda.getTipoDeVenda().length() > 20) {
            throw new IllegalArgumentException("O tipo de venda tem o nome muito grande e é inválido.");
        }
        if (!user.isAdmin()) {
            throw new RuntimeException("O usuário não é um admin");
        }
        //repo validations
        else if (!userRepo.existsById(user.getId())) {
            throw new RuntimeException("O usuário não existe");
        }
        //Patterns
        else if (!EMAIL_USUARIO_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("O email do usuário é inválido");
        } else if (!NOME_USUARIO_PATTERN.matcher(user.getUserName()).matches()) {
            throw new IllegalArgumentException("O nome do usuario é inválido");
        }
        //Lengths
        else if (user.getUserName().length() > 20) {
            throw new IllegalArgumentException("O nome do usuário é muito grande. max = 20");
        } else if (user.getEmail().length() > 255) {
            throw new IllegalArgumentException("O email do usuário é muito grande. max = 255");
        }
        else if (user.getId() == null) {
            throw new RuntimeException("O usuário não possui um id ou não foi informado.");
        }

        List<Produto> produtosVenda = venda.getProdutos();
        //adiciona o produto a lista
        produtosVenda.add(produto1);
    }

    public Venda cadastrarVenda(UsuarioDTO user, VendaDTO dto, List<ProdutoDTO> produtos) {
        Venda novaVenda = new Venda();

        //Validar usuario
        if (!user.isAdmin()) {
            throw new IllegalArgumentException("O usuário não tem permissão para cadastrar uma venda");
        }else if (userRepo.existsById(user.getId())) {
            throw new IllegalArgumentException("O usuário não existe ou não foi encontrado");
        }
        //Patterns
        else if (!NOME_USUARIO_PATTERN.matcher(user.getUserName()).matches()) {
            throw new IllegalArgumentException("O nome do usuário é inválido");
        } else if (!EMAIL_USUARIO_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("O email do usuário é inválido");
        }
        //Lengths
        else if (user.getUserName().length() > 20) {
            throw new IllegalArgumentException("O nome do cliente é muito grande. max = 20");
        } else if (user.getEmail().length() > 255) {
            throw new IllegalArgumentException("O email do usuario cadastrado é muito grande");
        }
        novaVenda.setTipoDeVenda(dto.getTipoDeVenda());
        novaVenda.setDescricao(dto.getDescricao());
        novaVenda.setDataVenda(LocalDateTime.now());
        novaVenda.setPlano(dto.getPlano());
        novaVenda.setVendedor(dto.getVendedor());

        List<ProdutoDTO> produtoList = produtos;



        return vendaRepo.save(novaVenda);
    }

}
