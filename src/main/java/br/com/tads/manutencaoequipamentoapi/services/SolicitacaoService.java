package br.com.tads.manutencaoequipamentoapi.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.commom.Response;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.SolicitacaoDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.SolicitacaoFormDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Cliente;
import br.com.tads.manutencaoequipamentoapi.entities.entity.EstadoSolicitacao;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Funcionario;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Movimentacao;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Role;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Solicitacao;
import br.com.tads.manutencaoequipamentoapi.entities.entity.User;
import br.com.tads.manutencaoequipamentoapi.repositories.FuncionarioRepository;
import br.com.tads.manutencaoequipamentoapi.repositories.MovimentacaoRepository;
import br.com.tads.manutencaoequipamentoapi.repositories.SolicitacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SolicitacaoService {
    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @Transactional(rollbackOn = Exception.class)
    public Solicitacao registrar(SolicitacaoFormDTO dto) {
        Cliente cliente = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Solicitacao solicitacao = Solicitacao.builder()
                                  .categoria(new Categoria(dto.idCategoria()))
                                  .client(cliente)
                                  .descricaoEquipamento(dto.descricaoEquipamento())
                                  .descricaoProblema(dto.descricaoEquipamento())
                                  .build();
        solicitacao.setEstadoAtual(EstadoSolicitacao.ABERTA);
        solicitacao = solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return solicitacao;
    }

    public Response visualizar(Long id) {
        return new Response(true, solicitacaoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação")));
    }

    public List<SolicitacaoDTO> visualizar() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SolicitacaoDTO> solicitacoes = new ArrayList<SolicitacaoDTO>();
        if (user.getRole().equals(Role.CLIENT)) {
            solicitacoes = solicitacaoRepository.findByClient_id(user.getId()).stream().map(s -> new SolicitacaoDTO(s)).collect(Collectors.toList());
        } else if (user.getRole().equals(Role.FUNCIONARIO)) {
            solicitacoes = solicitacaoRepository.findByFuncionario_id(user.getId()).stream().map(s -> new SolicitacaoDTO(s)).collect(Collectors.toList());
        }
        return solicitacoes;
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO aprovar(SolicitacaoFormDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.APROVADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO rejeitar(SolicitacaoFormDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.REJEITADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO resgatar(SolicitacaoFormDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.FINALIZADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO pagar(SolicitacaoFormDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.PAGA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO efetuarOrcamento(SolicitacaoFormDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.ORCADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO redirecionar(SolicitacaoFormDTO dto) {
        funcionarioRepository.findById(dto.idFuncionario())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar funcionario"));
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.REDIRECIONADA);
        solicitacao.setFuncionario(new Funcionario(dto.idFuncionario()));
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO finalizar(SolicitacaoFormDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.FINALIZADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    public void salvarMovimentacao(Solicitacao solicitacao) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setDtHrMovimentacao(LocalDateTime.now());
        movimentacao.setSolicitacao(solicitacao);
        movimentacao.setEstadoMovimentacao(solicitacao.getEstadoAtual());
        if (user.getRole().equals(Role.FUNCIONARIO)) {
            movimentacao.setFuncionario((Funcionario) user);
        }
        movimentacaoRepository.save(movimentacao);
    }
}