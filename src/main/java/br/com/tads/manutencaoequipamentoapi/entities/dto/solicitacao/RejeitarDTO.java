package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;


import jakarta.validation.constraints.NotBlank;

public record RejeitarDTO(
        @NotBlank String justificativaRejeicao ) {
}
