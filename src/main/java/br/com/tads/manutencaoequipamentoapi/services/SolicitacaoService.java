package br.com.tads.manutencaoequipamentoapi.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.commom.Response;
import br.com.tads.manutencaoequipamentoapi.entities.dto.SolicitacaoDTO;
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

    public Response registrar(SolicitacaoDTO dto) {
        Cliente cliente = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Solicitacao solicitacao = new Solicitacao(dto);
        solicitacao.setClient(cliente);
        solicitacao.setEstadoAtual(EstadoSolicitacao.ABERTA);
        salvarMovimentacao(solicitacao);
        return new Response(true, "Solicitação salva com sucesso");
    }

    public Response visualizar(Long id) {
        return new Response(true, solicitacaoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação")));
    }

    public Response visualizar() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
        if (user.getRole().equals(Role.CLIENT)) {
            solicitacoes = solicitacaoRepository.findByClient_id(user.getId());
        } else if (user.getRole().equals(Role.FUNCIONARIO)) {
            solicitacoes = solicitacaoRepository.findByFuncionario_id(user.getId());
        }

        return new Response(true, solicitacoes);
    }

    @Transactional(rollbackOn = Exception.class)
    public Response aprovar(SolicitacaoDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.APROVADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new Response(true, "Solicitação aprovada com sucesso");
    }

    @Transactional(rollbackOn = Exception.class)
    public Response rejeitar(SolicitacaoDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.REJEITADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new Response(true, "Solicitação rejeitada com sucesso");
    }

    @Transactional(rollbackOn = Exception.class)
    public Response resgatar(SolicitacaoDTO solicitacaoDTO) {
        return new Response();
    }

    @Transactional(rollbackOn = Exception.class)
    public Response pagar(SolicitacaoDTO solicitacaoDTO) {
        return new Response();
    }

    @Transactional(rollbackOn = Exception.class)
    public Response efetuarOrcamento(SolicitacaoDTO solicitacaoDTO) {
        return new Response();
    }

    @Transactional(rollbackOn = Exception.class)
    public Response redirecionar(SolicitacaoDTO dto) {
        funcionarioRepository.findById(dto.idFuncionario())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar funcionario"));
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.REDIRECIONADA);
        solicitacao.setFuncionario(new Funcionario(dto.idFuncionario()));
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new Response(true, "Solicitação redirecionada com sucesso");
    }

    @Transactional(rollbackOn = Exception.class)
    public Response finalizar(SolicitacaoDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.FINALIZADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new Response(true, "Solicitação finalizada com sucesso");
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