package br.com.tads.manutencaoequipamentoapi.models.dto.solicitacao;

import jakarta.validation.constraints.NotNull;

public record RedirecionarDTO(
        @NotNull (message = "é obrigatório selecionar um funcionário") Long idFuncionario) {

}