package br.com.tads.manutencaoequipamentoapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByCpf(String email);
    
}
