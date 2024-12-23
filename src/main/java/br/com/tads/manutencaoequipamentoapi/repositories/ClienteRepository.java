package br.com.tads.manutencaoequipamentoapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tads.manutencaoequipamentoapi.models.entity.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByCpf(String email);

    Optional<Cliente> findByCpfAndStatus(String cpf, boolean b);

}
