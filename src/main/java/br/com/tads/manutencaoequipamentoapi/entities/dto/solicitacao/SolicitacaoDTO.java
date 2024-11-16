package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;
import br.com.tads.manutencaoequipamentoapi.entities.entity.EstadoSolicitacao;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Funcionario;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Solicitacao;

public record SolicitacaoDTO (Long id,
                              EstadoSolicitacao estadoAtual,
                              String idCategoria,
                              String funcionario , 
                              Long idFuncionario , 
                              String cliente,
                              Long idCliente,
                              String descricaoEquipamento,
                              LocalDateTime dtHrCriacao,
                              String descricaoProblema,
                              BigDecimal valor) {
    public  SolicitacaoDTO(Solicitacao solicitacao) {
        this(
            solicitacao.getId(),
            solicitacao.getEstadoAtual(),
            Optional.ofNullable(solicitacao.getCategoria()).map(Categoria::getDescricao).orElse(""),
            Optional.ofNullable(solicitacao.getFuncionario()).map(Funcionario::getNome).orElse(""), 
            Optional.ofNullable(solicitacao.getFuncionario()).map(Funcionario::getId).orElse(null), 
            solicitacao.getClient().getNome(),
            solicitacao.getClient().getId(),
            solicitacao.getDescricaoEquipamento(),
            solicitacao.getDtHrCriacao(),
            solicitacao.getDescricaoProblema(),
            solicitacao.getValorOrcamento()
        );
    }
}
