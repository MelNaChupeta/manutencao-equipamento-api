package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;


public record SolicitacaoFormDTO(Long id,
                                 Long idCategoria,
                                 Long idFuncionario,
                                 String descricaoEquipamento,
                                 String descricaoProblema ,
                                 String descricaoRejeicao ,
                                 String descricaoManutencao,
                                 String orientacoesCliente;
                                 BigDecimal valorOrcamento) {
   
}
