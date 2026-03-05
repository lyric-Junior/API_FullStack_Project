package com.host.server.repository;


import com.host.server.model.Entitys.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> searchById(Long id);

    Optional<Produto> findByNomeContainingIgnoreCase(String nome);
}
