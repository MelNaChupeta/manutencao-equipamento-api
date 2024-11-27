package br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.tads.manutencaoequipamentoapi.entities.dto.categoria.CategoriaDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;
import br.com.tads.manutencaoequipamentoapi.entities.entity.EstadoSolicitacao;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Funcionario;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Solicitacao;

public record SolicitacaoDTO (Long id,
                              String estadoAtual,
                              CategoriaDTO categoria,
                              String funcionario , 
                              Long idFuncionario , 
                              String cliente,
                              Long idCliente,
                              String descricaoEquipamento,
                              LocalDateTime dtHrCriacao,
                              String descricaoProblema,
                              OrcamentoDTO orcamento,
                              String descricaoManutencao,
                              String orientacaoCliente,
                              List<MovimentacaoDTO> historicoMovimentacao) {
    public  SolicitacaoDTO(Solicitacao solicitacao) {
        this(
            solicitacao.getId(),
            solicitacao.getEstadoAtual().getEstado(),
            new CategoriaDTO(
                solicitacao.getCategoria()
            ),
            Optional.ofNullable(solicitacao.getFuncionario()).map(Funcionario::getNome).orElse(""), 
            Optional.ofNullable(solicitacao.getFuncionario()).map(Funcionario::getId).orElse(null), 
            solicitacao.getClient().getNome(),
            solicitacao.getClient().getId(),
            solicitacao.getDescricaoEquipamento(),
            solicitacao.getDtHrCriacao(),
            solicitacao.getDescricaoProblema(),
            new OrcamentoDTO(solicitacao.getDescricaoRejeicao(),solicitacao.getValorOrcamento()),
            solicitacao.getDescricaoManutencao(),
            solicitacao.getOrientacoesCliente(),
            solicitacao.getHistoricoMovimentacao().stream()
            .map(MovimentacaoDTO::new) 
            .collect(Collectors.toList())
        );
    }
}
