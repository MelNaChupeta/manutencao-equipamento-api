package br.com.tads.manutencaoequipamentoapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    
}
