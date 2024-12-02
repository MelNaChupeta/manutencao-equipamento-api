package br.com.tads.manutencaoequipamentoapi.models.dto.solicitacao;

import java.math.BigDecimal;

public record OrcamentoDTO(
        String justificativaRejeicao,
        BigDecimal valorOrcamento) {

}