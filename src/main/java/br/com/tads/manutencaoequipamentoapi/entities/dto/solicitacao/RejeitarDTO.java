package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RejeitarDTO(
        @NotNull Long id,
        @NotBlank String descricaoRejeicao ) {
    
}
