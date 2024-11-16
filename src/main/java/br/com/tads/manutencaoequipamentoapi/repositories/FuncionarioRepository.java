package br.com.tads.manutencaoequipamentoapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario , Long>{

    List<Funcionario> findByStatus(boolean b);

    Optional<Funcionario> findByIdAndStatus(Long id, boolean b);
    
}
