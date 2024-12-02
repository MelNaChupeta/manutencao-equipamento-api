package br.com.tads.manutencaoequipamentoapi.models.dto.solicitacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RegistrarSolicitacaoDTO(
                                 @NotEmpty Long idCategoria,
                                 @NotBlank(message = "A descrição do equipamento é obrigatória") String descricaoEquipamento,
                                 @NotBlank(message = "A descrição do problema é obrigatória") String descricaoProblema ) {
   
}
