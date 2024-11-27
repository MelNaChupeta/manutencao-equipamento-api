package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.tads.manutencaoequipamentoapi.entities.entity.EstadoSolicitacao;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Movimentacao;
import br.com.tads.manutencaoequipamentoapi.entities.entity.User;

public record MovimentacaoDTO (@JsonFormat() LocalDateTime dtHrMovimentacao,
                               EstadoSolicitacao estadoMovimentacao,
                               User autorMovimentacao ){
    public MovimentacaoDTO (Movimentacao movimentacao) {
          
        this(
            movimentacao.getDtHrMovimentacao(),
            movimentacao.getEstadoMovimentacao(),
            movimentacao.getFuncionario() != null ? 
                movimentacao.getFuncionario() : movimentacao.getSolicitacao().getClient()
        );
    }
}
