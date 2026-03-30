package com.host.server.repository;


import com.host.server.model.entitys.Cliente;
import com.host.server.model.entitys.Usuario;
import com.host.server.model.entitys.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByVendedor(Usuario vendedor);
    List<Venda> findByCliente(Cliente cliente);
    List<Venda> findByDataVendaBetween(LocalDateTime inicio, LocalDateTime fim);
}