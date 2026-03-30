package com.host.server.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class VendaDTO {
    private Long clienteId;
    private Long planoId;
    private String tipoDeVenda;
    private String descricao;
    private boolean gerouDivida;
    private List<VendaItemDTO> itens;
}