package br.com.tads.manutencaoequipamentoapi.models.dto.solicitacao;


import jakarta.validation.constraints.NotBlank;

public record RejeitarDTO(
        @NotBlank String justificativaRejeicao ) {
}
