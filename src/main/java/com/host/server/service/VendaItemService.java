package com.host.server.service;

import com.host.server.model.dto.UsuarioDTO;
import com.host.server.model.dto.VendaItemDTO;
import com.host.server.model.entitys.*;
import com.host.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaItemService {

    @Autowired
    private VendaItemRepository vendaItemRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public VendaItemDTO atualizarQuantidadeItem(Long itemId, Integer novaQuantidade, UsuarioDTO vendedorDTO) {
        validationService.validateUser(vendedorDTO);

        VendaItem item = vendaItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if (novaQuantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        item.setQuantidade(novaQuantidade);

        VendaItem itemAtualizado = vendaItemRepository.save(item);

        atualizarTotalVenda(item.getVenda());

        return converterParaDTO(itemAtualizado);
    }

    @Transactional
    public VendaItemDTO atualizarPrecoItem(Long itemId, BigDecimal novoPreco, UsuarioDTO vendedorDTO) {
        validationService.validateUser(vendedorDTO);

        VendaItem item = vendaItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if (novoPreco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        item.setPrecoUnitario(novoPreco);
        VendaItem itemAtualizado = vendaItemRepository.save(item);

        atualizarTotalVenda(item.getVenda());

        return converterParaDTO(itemAtualizado);
    }

    public List<VendaItemDTO> listarItensPorVenda(Long vendaId, UsuarioDTO vendedorDTO) {
        validationService.validateUser(vendedorDTO);

        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        return venda.getItens().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal calcularSubtotalItem(VendaItem item) {
        if (item.getQuantidade() == null || item.getPrecoUnitario() == null) {
            return BigDecimal.ZERO;
        }
        return item.getPrecoUnitario()
                .multiply(BigDecimal.valueOf(item.getQuantidade()));
    }

    private void atualizarTotalVenda(Venda venda) {
        BigDecimal novoTotal = venda.getItens().stream()
                .map(item -> item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Se quiser salvar o total na tabela Venda (campo opcional)
        // venda.setTotalVenda(novoTotal);
        vendaRepository.save(venda);
    }

    private VendaItemDTO converterParaDTO(VendaItem item) {
        VendaItemDTO dto = new VendaItemDTO();
        dto.setId(item.getId());
        dto.setProdutoId(item.getProduto().getId());
        dto.setProdutoNome(item.getProduto().getNome());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setSubtotal(calcularSubtotalItem(item));
        return dto;
    }
}