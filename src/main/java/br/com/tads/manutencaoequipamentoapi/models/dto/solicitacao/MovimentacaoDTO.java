package br.com.tads.manutencaoequipamentoapi.models.dto.solicitacao;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.tads.manutencaoequipamentoapi.models.dto.user.UserDTO;
import br.com.tads.manutencaoequipamentoapi.models.entity.Movimentacao;

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
