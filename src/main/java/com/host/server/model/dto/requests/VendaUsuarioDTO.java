package com.host.server.model.dto.requests;

import com.host.server.model.dto.UsuarioDTO;
import com.host.server.model.dto.VendaDTO;
import lombok.Getter;
import lombok.Setter;

public class VendaUsuarioDTO {
    @Getter @Setter
    private UsuarioDTO usuario;
    @Getter @Setter
    private VendaDTO venda;
}
