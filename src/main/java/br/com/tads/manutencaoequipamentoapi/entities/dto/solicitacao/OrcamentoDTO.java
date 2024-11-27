package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;

import java.math.BigDecimal;

public record OrcamentoDTO(
        String justificativaRejeicao,
        BigDecimal valorOrcamento) {

}