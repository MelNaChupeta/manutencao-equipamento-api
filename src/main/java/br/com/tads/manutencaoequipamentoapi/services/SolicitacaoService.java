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

	public Response register(SolicitacaoDTO dto)  {
        Cliente cliente = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Solicitacao solicitacao = new Solicitacao(dto);
        solicitacao.setClient(cliente);
        solicitacao.setEstadoAtual(EstadoSolicitacao.ABERTA);
        saveMovimentacao(solicitacao);
		return new Response(true , "Solicitação salva com sucesso");
	}

	public Response visualizar(Long id)  {
		return new Response(true , solicitacaoRepository
                                            .findById(id)
                                            .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação")));
	}
	
    public Response visualizar()  {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
        if(user.getRole().equals(Role.CLIENT)) {
            solicitacoes = solicitacaoRepository.findByClient_id(user.getId());
        }else if(user.getRole().equals(Role.FUNCIOARIO)) {
            solicitacoes = solicitacaoRepository.findByFuncionario_id(user.getId());
        }

		return new Response(true , solicitacoes);
	}
   
    @Transactional(rollbackOn = Exception.class)
	public Response aprovar(SolicitacaoDTO dto)  {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                                            .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        solicitacao.setEstadoAtual(EstadoSolicitacao.APROVADA);
        solicitacaoRepository.save(solicitacao);
        saveMovimentacao(solicitacao);
		return new Response(true,"Solicitação aprovada com sucesso");
	}

    
    
    public void saveMovimentacao(Solicitacao solicitacao){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setDtHrMovimentacao(LocalDateTime.now());
        movimentacao.setSolicitacao(solicitacao);
        movimentacao.setEstadoMovimentacao(solicitacao.getEstadoAtual());
        if(user.getRole().equals(Role.FUNCIOARIO)) {
            movimentacao.setFuncionario((Funcionario) user);
        }
        movimentacaoRepository.save(movimentacao);
    }
}
