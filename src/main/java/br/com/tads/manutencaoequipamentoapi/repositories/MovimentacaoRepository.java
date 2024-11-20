package br.com.tads.manutencaoequipamentoapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Movimentacao;
@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long>{
    
}
