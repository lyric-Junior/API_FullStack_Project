package com.host.server.repository;


import com.host.server.model.Entitys.PlanoDeNegocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanoDeNegocioRepository extends JpaRepository<PlanoDeNegocio, Long> {

    Optional<PlanoDeNegocio> findByNameContainingIgnoreCase(String name);
}
