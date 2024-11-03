package br.com.tads.manutencao_equipamento_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tads.manutencao_equipamento_api.entities.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario , Long>{

    Funcionario findByStatus(boolean b);
    
}
