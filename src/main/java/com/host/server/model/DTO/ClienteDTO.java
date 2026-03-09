package com.host.server.model.DTO;


import lombok.Getter;
import lombok.Setter;

public class ClienteDTO {

    @Getter @Setter
    private String nome;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String cpf;
    @Getter @Setter
    private String RG;
    @Getter
    private String divida;

    public ClienteDTO() {};

    public void setDivida() {this.divida = divida;}
}
