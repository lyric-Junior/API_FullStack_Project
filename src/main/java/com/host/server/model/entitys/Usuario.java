package com.host.server.model.entitys;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter @Setter
    @Column(unique = true, nullable = false, length = 20)
    private String userName;

    @Getter @Setter
    @Column
    private String email;

    @Getter @Setter
    @Column(unique = true, nullable = true)
    public boolean admin;

    @Getter @Setter
    @Column(nullable = false)
    private String senhaHash;

    @Getter @Setter
    @Column
    private String cpfHash;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dataDeCadastro;

    @Getter @Setter
    @OneToMany(mappedBy = "vendedor")
    private List<Venda> vendas;
}
