package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record OrcamentoDTO(
        @NotNull Long id,
        @NotNull BigDecimal valorOrcamento) {

}