package br.com.tads.manutencaoequipamentoapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario , Long>{

    Funcionario findByStatus(boolean b);
    
}
