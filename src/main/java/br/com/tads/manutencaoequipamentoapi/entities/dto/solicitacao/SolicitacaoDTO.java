package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;
import br.com.tads.manutencaoequipamentoapi.entities.entity.EstadoSolicitacao;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Funcionario;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Solicitacao;

public record SolicitacaoDTO (Long id,
                              EstadoSolicitacao estadoAtual,
                              Long idCategoria,
                              String nomeCategoria,
                              String funcionario , 
                              Long idFuncionario , 
                              String cliente,
                              Long idCliente,
                              String descricaoEquipamento,
                              @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
                              LocalDateTime dtHrCriacao,
                              String descricaoProblema,
                              BigDecimal valor,
                              String descricaoRejeicao,
                              String descricaoManutencao,
                              String orientacaoCliente) {
    public  SolicitacaoDTO(Solicitacao solicitacao) {
        this(
            solicitacao.getId(),
            solicitacao.getEstadoAtual(),
            Optional.ofNullable(solicitacao.getCategoria()).map(Categoria::getId).orElse(null),
            Optional.ofNullable(solicitacao.getCategoria()).map(Categoria::getDescricao).orElse(""),
            Optional.ofNullable(solicitacao.getFuncionario()).map(Funcionario::getNome).orElse(""), 
            Optional.ofNullable(solicitacao.getFuncionario()).map(Funcionario::getId).orElse(null), 
            solicitacao.getClient().getNome(),
            solicitacao.getClient().getId(),
            solicitacao.getDescricaoEquipamento(),
            solicitacao.getDtHrCriacao(),
            solicitacao.getDescricaoProblema(),
            solicitacao.getValorOrcamento(),
            solicitacao.getDescricaoRejeicao(),
            solicitacao.getDescricaoManutencao(),
            solicitacao.getOrientacoesCliente()
        );
    }
}
