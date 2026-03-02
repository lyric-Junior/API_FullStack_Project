package com.host.server.repository;


import com.host.server.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findAll();

    Optional<Cliente> searchById(Long id);

    Optional<Cliente> findByNomeContainingIgnoreCase(String nome);
}
