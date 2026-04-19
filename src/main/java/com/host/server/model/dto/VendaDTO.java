package com.host.server.model.dto;

import com.host.server.model.entitys.PlanoDeNegocio;
import com.host.server.model.entitys.VendaItem;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class VendaDTO {
    private Long clienteId;
    private PlanoDeNegocio plano;
    private String tipoDeVenda;
    private String descricao;
    private boolean gerouDivida;
    private List<VendaItem> itens;
}