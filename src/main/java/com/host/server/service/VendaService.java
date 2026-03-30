package com.host.server.service;

import com.host.server.model.dto.UsuarioDTO;
import com.host.server.model.dto.VendaDTO;
import com.host.server.model.dto.VendaItemDTO;
import com.host.server.model.dto.VendaResponseDTO;
import com.host.server.model.entitys.*;
import com.host.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private VendaItemRepository vendaItemRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoDeNegocioRepository planoRepository;

    private BigDecimal calcularTotalVenda(Venda venda) {
        if (venda.getItens() == null || venda.getItens().isEmpty()) {
            return BigDecimal.ZERO;
        }

        return venda.getItens().stream()
                .map(item -> item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private VendaResponseDTO converterParaResponseDTO(Venda venda, BigDecimal total) {
        VendaResponseDTO response = new VendaResponseDTO();
        response.setId(venda.getId());
        response.setDataVenda(venda.getDataVenda());
        response.setTipoDeVenda(venda.getTipoDeVenda());
        response.setDescricao(venda.getDescricao());
        response.setTotalVenda(total);

        if (venda.getCliente() != null) {
            response.setClienteId(venda.getCliente().getId());
            response.setClienteNome(venda.getCliente().getNome());
        }

        if (venda.getVendedor() != null) {
            response.setVendedorId(venda.getVendedor().getId());
            response.setVendedorNome(venda.getVendedor().getUserName());
        }

        if (venda.getPlano() != null) {
            response.setPlanoId(venda.getPlano().getId());
            response.setPlanoTipo(venda.getPlano().getTipoDePlano());
        }

        List<VendaItemDTO> itensDTO = venda.getItens().stream()
                .map(this::converterItemParaDTO)
                .collect(Collectors.toList());
        response.setItens(itensDTO);

        return response;
    }

    private VendaItemDTO converterItemParaDTO(VendaItem item) {
        VendaItemDTO dto = new VendaItemDTO();

        dto.setId(item.getId());
        dto.setProdutoId(item.getProduto().getId());
        dto.setProdutoNome(item.getProduto().getNome());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setSubtotal(item.getSubtotal());

        return dto;
    }

    @Transactional
    public VendaResponseDTO criarVenda(VendaDTO vendaDTO, UsuarioDTO user) {
        //User Repo
        Usuario vendedor = usuarioRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
        //User Repo
        Cliente cliente = clienteRepository.findById(vendaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        //Setters
        Venda venda = new Venda();
        venda.setVendedor(vendedor);
        venda.setCliente(cliente);
        venda.setDataVenda(LocalDateTime.now());
        venda.setTipoDeVenda(vendaDTO.getTipoDeVenda());
        venda.setDescricao(vendaDTO.getDescricao());

        if (vendaDTO.getPlanoId() != null) {
            PlanoDeNegocio plano = planoRepository.findById(vendaDTO.getPlanoId())
                    .orElseThrow(() -> new RuntimeException("Plan not found!"));
            venda.setPlano(plano);
        }

        Venda vendaSalva = vendaRepository.save(venda);

        BigDecimal totalVenda = BigDecimal.ZERO;

        for (VendaItemDTO itemDTO : vendaDTO.getItens()) {
            VendaItem item = criarItemVenda(itemDTO, vendaSalva);
            vendaSalva.getItens().add(item);
            totalVenda = totalVenda.add(item.getSubtotal());
        }

        if (vendaDTO.isGerouDivida()) {
            cliente.setDivida(true);
            clienteRepository.save(cliente);
        }

        Venda vendaFinal = vendaRepository.save(vendaSalva);

        return converterParaResponseDTO(vendaFinal, totalVenda);
    }

    @Transactional
    public VendaResponseDTO adicionarItemVenda(Long vendaId, VendaItemDTO itemDTO, UsuarioDTO vendedorDTO) {

        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new RuntimeException("Sale not found!"));

        if (venda.getDataVenda().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("It's not possible to add items in a sale with more than one day!");
        }

        VendaItem novoItem = criarItemVenda(itemDTO, venda);
        venda.getItens().add(novoItem);

        BigDecimal novoTotal = calcularTotalVenda(venda);

        Venda vendaAtualizada = vendaRepository.save(venda);

        return converterParaResponseDTO(vendaAtualizada, novoTotal);
    }

    @Transactional
    public void removerItemVenda(Long vendaId, Long itemId, UsuarioDTO vendedorDTO) {

        // 2. BUSCAR ITEM
        VendaItem item = vendaItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        // 3. VERIFICAR SE PERTENCE À VENDA
        if (!item.getVenda().getId().equals(vendaId)) {
            throw new RuntimeException("Item não pertence a esta venda");
        }

        // 4. REMOVER ITEM
        vendaItemRepository.delete(item);
    }

    // ==================== CONSULTAS ====================

    public VendaResponseDTO buscarVendaPorId(Long id, UsuarioDTO vendedorDTO) {

        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        BigDecimal total = calcularTotalVenda(venda);
        return converterParaResponseDTO(venda, total);
    }

    public List<VendaResponseDTO> listarVendasPorVendedor(UsuarioDTO vendedorDTO) {

        Usuario vendedor = usuarioRepository.findById(vendedorDTO.getId())
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));

        List<Venda> vendas = vendaRepository.findByVendedor(vendedor);

        return vendas.stream()
                .map(venda -> {
                    BigDecimal total = calcularTotalVenda(venda);
                    return converterParaResponseDTO(venda, total);
                })
                .collect(Collectors.toList());
    }

    public List<VendaResponseDTO> listarVendasPorCliente(Long clienteId, UsuarioDTO vendedorDTO) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        List<Venda> vendas = vendaRepository.findByCliente(cliente);

        return vendas.stream()
                .map(venda -> {
                    BigDecimal total = calcularTotalVenda(venda);
                    return converterParaResponseDTO(venda, total);
                })
                .collect(Collectors.toList());
    }

    // ==================== MÉTODOS PRIVADOS ====================

    private VendaItem criarItemVenda(VendaItemDTO itemDTO, Venda venda) {
        // 1. BUSCAR PRODUTO
        Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoId()));

        // 2. VALIDAR ESTOQUE (se tiver)
        if (itemDTO.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        // 3. CRIAR ITEM
        VendaItem item = new VendaItem();
        item.setVenda(venda);
        item.setProduto(produto);
        item.setQuantidade(itemDTO.getQuantidade());

        // 4. DEFINIR PREÇO (pode vir do DTO ou do produto)
        if (itemDTO.getPrecoUnitario() != null) {
            item.setPrecoUnitario(itemDTO.getPrecoUnitario());
        } else {
            item.setPrecoUnitario(produto.getValor());
        }

        return item;
    }
}