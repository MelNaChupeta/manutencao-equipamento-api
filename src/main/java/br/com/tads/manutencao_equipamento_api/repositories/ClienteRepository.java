package br.com.tads.manutencao_equipamento_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tads.manutencao_equipamento_api.entities.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    
}
