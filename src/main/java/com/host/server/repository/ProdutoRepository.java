package com.host.server.repository;


import com.host.server.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findAll();

    Optional<Produto> searchById(Long id);

    Optional<Produto> findByNomeContainingIgnoreCase(String nome);
}
