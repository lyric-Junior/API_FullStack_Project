package com.host.server.repository;


import com.host.server.model.entitys.PlanoDeNegocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanoDeNegocioRepository extends JpaRepository<PlanoDeNegocio, Long> {

    Optional<PlanoDeNegocio> findByTipoDePlanoContainingIgnoreCase(String name);
}
