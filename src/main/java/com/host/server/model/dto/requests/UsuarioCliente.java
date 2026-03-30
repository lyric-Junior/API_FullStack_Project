package com.host.server.model.dto.requests;

import com.host.server.model.dto.ClienteDTO;
import com.host.server.model.dto.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;


public class UsuarioCliente {
    @Getter @Setter
    private UsuarioDTO usuario;
    @Getter @Setter
    private ClienteDTO cliente;
}
