package br.com.tads.manutencaoequipamentoapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long>{
    
}
