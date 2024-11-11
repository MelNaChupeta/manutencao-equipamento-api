package br.com.tads.manutencaoequipamentoapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Solicitacao;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao , Long>{

    List<Solicitacao> findByClient_id(Long id);

    List<Solicitacao> findByFuncionario_id(Long id);
    
}
