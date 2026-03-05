package com.host.server.repository;
//import classe cliente
import com.host.server.model.Entitys.Cliente;
//unico import necessário para extender a interface com JpaRepository
import org.springframework.data.jpa.repository.JpaRepository;
//utilizado para buscar listas clientes
import java.util.Optional;


public interface ClienteRepository
    extends JpaRepository<Cliente, Long> {
    //outros métodos são aplicados diretamente no JpaRepository que já entrega diversos métodos prontos
    Optional<Cliente> findByNomeContainingCase(String nome);
}

