package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;

import java.time.LocalDateTime;

public record SolicitacaoFormDTO(Long id,Long idCategoria,Long idFuncionario,String descricaoEquipamento,LocalDateTime dtHrCriacao,String descricaoProblema) {
   
}
