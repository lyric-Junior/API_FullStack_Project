package com.host.server.model.DTO;


import lombok.Getter;
import lombok.Setter;

public class UsuarioDTO {

    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String userName;
    @Getter @Setter
    private String senhaHash;
}
