package com.host.server.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class VendaResponseDTO {
    private Long id;
    private LocalDateTime dataVenda;
    private String tipoDeVenda;
    private String descricao;
    private BigDecimal totalVenda;

    // Cliente
    private Long clienteId;
    private String clienteNome;

    // Vendedor
    private Long vendedorId;
    private String vendedorNome;

    // Plano
    private Long planoId;
    private String planoTipo;

    // Itens
    private List<VendaItemDTO> itens;
}