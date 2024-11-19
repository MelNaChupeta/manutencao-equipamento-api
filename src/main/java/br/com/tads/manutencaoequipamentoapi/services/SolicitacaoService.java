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
import br.com.tads.manutencaoequipamentoapi.exceptions.ValidationException;
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
    
    public List<SolicitacaoDTO> visualizar(boolean todas,boolean hoje,LocalDate dataAbertura) {
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
    public SolicitacaoDTO efetuarOrcamento(SolicitacaoFormDTO dto) {
        if(dto.valorOrcamento() == null && !(dto.valorOrcamento() > 0))
                throw new ValidationException("este valor de orçamento é inválido");
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.ABERTA))
                throw new ValidationException("o orçamento já foi realizado");

        solicitacao.setEstadoAtual(EstadoSolicitacao.ORCADA);
        solicitacao.setValorOrcamento(dto.valorOrcamento());
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO aprovar(SolicitacaoFormDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.ORCADA))
                throw new ValidationException("o orçamento já foi analisado");

        solicitacao.setEstadoAtual(EstadoSolicitacao.APROVADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO rejeitar(SolicitacaoFormDTO dto) {
        if(dto.descricaoRejeicao().isEmpty())
            throw new ValidationException("a descrição da rejeicao é obrigatória");
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.ORCADA))
                throw new ValidationException("o orçamento já foi analisado");

        solicitacao.setEstadoAtual(EstadoSolicitacao.REJEITADA);
        solicitacao.setDescricaoRejeicao(dto.descricaoRejeicao());
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }
    
    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO efetuarManutencao(SolicitacaoFormDTO dto) {
        if(dto.descricaoManutencao().isEmpty())
            throw new ValidationException("a descrição da manutencao é obrigatória");
        if(dto.orientacoesCliente().isEmpty())
            throw new ValidationException("a orientação para o cliente é obrigatória");
        
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.APROVADA))
                throw new ValidationException("o orçamento ainda não foi aprovado");

        solicitacao.setEstadoAtual(EstadoSolicitacao.ARRUMADA);
        solicitacao.setDescricaoManutencao(dto.descricaoManutencao());
        solicitacao.setOrientacoesCliente(dto.orientacoesCliente());
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO resgatar(SolicitacaoFormDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.REJEITADA))
                throw new ValidationException("o equipamento já foi resgatado");
        
        solicitacao.setEstadoAtual(EstadoSolicitacao.APROVADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return new SolicitacaoDTO(solicitacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public SolicitacaoDTO pagar(SolicitacaoFormDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.ARRUMADA))
            throw new ValidationException("o equipamento ainda não foi arrumado");
        
        solicitacao.setEstadoAtual(EstadoSolicitacao.PAGA);
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