package br.com.tads.manutencaoequipamentoapi.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.commom.Response;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.ManutencaoFormDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.OrcamentoFormDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.RedirecionarDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.RegistrarSolicitacaoDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.RejeitarDTO;
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
import br.com.tads.manutencaoequipamentoapi.entities.specification.SolicitacaoSpecification;
import br.com.tads.manutencaoequipamentoapi.exceptions.ValidationException;
import br.com.tads.manutencaoequipamentoapi.repositories.CategoriaRepository;
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
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Transactional(rollbackOn = Exception.class)
    public Solicitacao registrar(RegistrarSolicitacaoDTO dto) {
        Cliente cliente = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        Solicitacao solicitacao = Solicitacao.builder()
                                  .categoria(categoria)
                                  .client(cliente)
                                  .descricaoEquipamento(dto.descricaoEquipamento())
                                  .descricaoProblema(dto.descricaoProblema())
                                  .build();
        solicitacao.setEstadoAtual(EstadoSolicitacao.ABERTA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return solicitacao;
    }

    public Solicitacao visualizar(Long id) {
        return solicitacaoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
    }

    public List<SolicitacaoDTO> visualizar() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SolicitacaoDTO> solicitacoes = new ArrayList<SolicitacaoDTO>();
        if (user.getRole().equals(Role.CLIENTE)) {
            solicitacoes = solicitacaoRepository.findByClient_id(user.getId()).stream().map(s -> new SolicitacaoDTO(s)).collect(Collectors.toList());
        } else if (user.getRole().equals(Role.FUNCIONARIO)) {
            solicitacoes = solicitacaoRepository.findByFuncionario_id(user.getId()).stream().map(s -> new SolicitacaoDTO(s)).collect(Collectors.toList());
        }
        return solicitacoes;
    }
    
    @Transactional
    public List<SolicitacaoDTO> visualizar(boolean todas, boolean hoje, LocalDate dataAberturaInicial ,  LocalDate dataAberturaFinal , String estado) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Long clientId = null;
        Long funcionarioId = null;

        if (user.getRole().equals(Role.CLIENTE)) {
            clientId = user.getId();
        } else if (user.getRole().equals(Role.FUNCIONARIO)) {
            funcionarioId = user.getId();
        }

        Specification<Solicitacao> spec = new SolicitacaoSpecification(clientId, funcionarioId, dataAberturaInicial , dataAberturaFinal, hoje, todas , estado);

        List<Solicitacao> solicitacoes = solicitacaoRepository.findAll(spec);

        return solicitacoes.stream().map(SolicitacaoDTO::new).collect(Collectors.toList());
    }

    @Transactional(rollbackOn = Exception.class)
    public Solicitacao efetuarOrcamento(OrcamentoFormDTO dto) throws ValidationException {
        Funcionario user = (Funcionario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(dto.valorOrcamento() == null && !(dto.valorOrcamento().compareTo(new BigDecimal(0)) > 0))
                throw new ValidationException("este valor de orçamento é inválido");
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.ABERTA))
                throw new ValidationException("o orçamento já foi realizado");

        solicitacao.setEstadoAtual(EstadoSolicitacao.ORCADA);
        solicitacao.setValorOrcamento(dto.valorOrcamento());
        solicitacao.setFuncionario(user);
        
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return solicitacao;
    }

    @Transactional(rollbackOn = Exception.class)
    public Solicitacao aprovar(Long id) throws ValidationException {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.ORCADA))
                throw new ValidationException("a solicitação já passou da etapa de aprovação");

        solicitacao.setEstadoAtual(EstadoSolicitacao.APROVADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return solicitacao;
    }

    @Transactional(rollbackOn = Exception.class)
    public Solicitacao rejeitar(RejeitarDTO dto , Long id) throws ValidationException {
       
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.ORCADA))
                throw new ValidationException("o orçamento já foi analisado");

        solicitacao.setEstadoAtual(EstadoSolicitacao.REJEITADA);
        solicitacao.setDescricaoRejeicao(dto.justificativaRejeicao());
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return solicitacao;
    }
    
    @Transactional(rollbackOn = Exception.class)
    public Solicitacao efetuarManutencao(ManutencaoFormDTO dto) throws ValidationException {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.APROVADA))
                throw new ValidationException("o orçamento ainda não foi aprovado");

        solicitacao.setEstadoAtual(EstadoSolicitacao.ARRUMADA);
        solicitacao.setDescricaoManutencao(dto.descricaoManutencao());
        solicitacao.setOrientacoesCliente(dto.orientacaoCliente());
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return solicitacao;
    }

    @Transactional(rollbackOn = Exception.class)
    public Solicitacao resgatar(Long id) throws ValidationException {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.REJEITADA))
                throw new ValidationException("o equipamento já foi resgatado");
        
        solicitacao.setEstadoAtual(EstadoSolicitacao.APROVADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return solicitacao;
    }

    @Transactional(rollbackOn = Exception.class)
    public Solicitacao pagar(Long id) throws ValidationException {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.ARRUMADA))
            throw new ValidationException("o equipamento ainda não foi arrumado");
        
        solicitacao.setEstadoAtual(EstadoSolicitacao.PAGA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return solicitacao;
    }

    @Transactional(rollbackOn = Exception.class)
    public Solicitacao redirecionar(RedirecionarDTO dto , Long idSolicitacao) throws ValidationException {
        funcionarioRepository.findById(dto.idFuncionario())
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar funcionario"));
        Solicitacao solicitacao = solicitacaoRepository.findById(idSolicitacao)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.APROVADA))
                throw new ValidationException("o equipamento ainda não foi aprovado pelo cliente ");
        
        solicitacao.setEstadoAtual(EstadoSolicitacao.REDIRECIONADA);
        solicitacao.setFuncionario(new Funcionario(dto.idFuncionario()));
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return solicitacao;
    }

    @Transactional(rollbackOn = Exception.class)
    public Solicitacao finalizar(Long id) throws ValidationException {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao encontrar solicitação"));
        
        if(!solicitacao.getEstadoAtual().equals(EstadoSolicitacao.PAGA))
                throw new ValidationException("o equipamento ainda não foi pago pelo cliente ");
        
        solicitacao.setEstadoAtual(EstadoSolicitacao.FINALIZADA);
        solicitacaoRepository.save(solicitacao);
        salvarMovimentacao(solicitacao);
        return solicitacao;
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