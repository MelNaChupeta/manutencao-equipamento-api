package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.tads.manutencaoequipamentoapi.entities.dto.user.UserDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Movimentacao;

public record MovimentacaoDTO (@JsonFormat() LocalDateTime dtHrMovimentacao,
                               String estadoMovimentacao,
                               UserDTO autorMovimentacao ){
    public MovimentacaoDTO (Movimentacao movimentacao) {
          
        this(
            movimentacao.getDtHrMovimentacao(),
            movimentacao.getEstadoMovimentacao().getEstado(),
             movimentacao.getFuncionario() != null ? 
                new UserDTO(movimentacao.getFuncionario())  : new UserDTO(movimentacao.getSolicitacao().getClient())
        );
    }
}
