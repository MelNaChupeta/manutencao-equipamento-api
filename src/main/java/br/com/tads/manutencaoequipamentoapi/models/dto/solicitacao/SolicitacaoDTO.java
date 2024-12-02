package br.com.tads.manutencaoequipamentoapi.models.dto.solicitacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import br.com.tads.manutencaoequipamentoapi.models.dto.categoria.CategoriaDTO;
import br.com.tads.manutencaoequipamentoapi.models.dto.cliente.ClienteDTO;
import br.com.tads.manutencaoequipamentoapi.models.entity.Funcionario;
import br.com.tads.manutencaoequipamentoapi.models.entity.Solicitacao;

public record SolicitacaoDTO (Long id,
                              String estadoAtual,
                              CategoriaDTO categoria,
                              String funcionario , 
                              Long idFuncionario , 
                              ClienteDTO cliente,
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
            new ClienteDTO(solicitacao.getClient()),
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
