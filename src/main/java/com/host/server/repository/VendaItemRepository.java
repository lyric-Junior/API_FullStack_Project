package com.host.server.repository;

import com.host.server.model.entitys.VendaItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaItemRepository extends JpaRepository<VendaItem, Long> {
    List<VendaItem> findByVendaId(Long vendaId);
    void deleteByVendaId(Long vendaId);
}
