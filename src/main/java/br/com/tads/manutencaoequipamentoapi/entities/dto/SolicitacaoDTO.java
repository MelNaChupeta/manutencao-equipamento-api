package br.com.tads.manutencaoequipamentoapi.entities.dto;

import java.time.LocalDateTime;

public record SolicitacaoDTO(Long id,Long idCategoria,Long idFuncionario,String descricaoEquipamento,LocalDateTime dtHrCriacao,String descricaoProblema) {
    
}
