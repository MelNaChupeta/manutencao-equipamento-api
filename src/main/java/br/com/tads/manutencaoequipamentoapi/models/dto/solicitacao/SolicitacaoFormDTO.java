package br.com.tads.manutencaoequipamentoapi.models.dto.solicitacao;

import java.math.BigDecimal;

public record SolicitacaoFormDTO(Long id,
                                 Long idCategoria,
                                 Long idFuncionario,
                                 String descricaoEquipamento,
                                 String descricaoProblema ,
                                 String descricaoRejeicao ,
                                 String descricaoManutencao,
                                 String orientacoesCliente,
                                 BigDecimal valorOrcamento) {
   
}
