package com.host.server.repository;


import com.host.server.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendaRepository  extends JpaRepository<Venda, Long> {

    List<Venda> getVendas();
    Optional<Venda> getVendaById(Long id);
    Optional<Venda> getVendaByNomeContainingIgnoreCase(String nome);


}
