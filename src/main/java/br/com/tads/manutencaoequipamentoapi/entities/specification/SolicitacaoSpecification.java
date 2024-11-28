package br.com.tads.manutencaoequipamentoapi.entities.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.tads.manutencaoequipamentoapi.entities.entity.EstadoSolicitacao;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Solicitacao;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SolicitacaoSpecification implements Specification<Solicitacao> {

    private Long clientId;
    private Long funcionarioId;
    private LocalDate dataAberturaInicial;
    private LocalDate dataAberturaFinal;
    private boolean hoje;
    private boolean todas;
    private EstadoSolicitacao estado;

    public SolicitacaoSpecification(Long clientId, Long funcionarioId, LocalDate dataAberturaInicial, LocalDate dataAberturaFinal, boolean hoje, boolean todas , String estado) {
        this.clientId = clientId;
        this.funcionarioId = funcionarioId;
        this.dataAberturaInicial = dataAberturaInicial;
        this.dataAberturaFinal = dataAberturaFinal;
        this.hoje = hoje;
        this.todas = todas;
        if(estado != null) 
            this.estado = EstadoSolicitacao.valueOf(estado.toUpperCase());
    }

    @Override
    public Predicate toPredicate(Root<Solicitacao> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = findByFilters(root, query, criteriaBuilder);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private List<Predicate> findByFilters(Root<Solicitacao> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (clientId != null) {
            predicates.add(criteriaBuilder.equal(root.get("client").get("id"), clientId));
        }

        if (funcionarioId != null) {
            predicates.add(criteriaBuilder.or(
                criteriaBuilder.equal(root.get("estadoAtual"), "ABERTA"),
                criteriaBuilder.equal(root.get("funcionario").get("id"), funcionarioId)
            ));
        }

        if (dataAberturaInicial != null && dataAberturaFinal != null) {
           // predicates.add(criteriaBuilder.equal(criteriaBuilder.function("DATE_TRUNC", LocalDate.class, criteriaBuilder.literal("day"), root.get("dtHrCriacao")), dataAberturaInicial));
        
            predicates.add(criteriaBuilder.between(
                root.get("dtHrCriacao").as(LocalDate.class),
                dataAberturaInicial,
                dataAberturaFinal
            ));
        }

        if (hoje) {
            LocalDate today = LocalDate.now();
            predicates.add(criteriaBuilder.equal(criteriaBuilder.function("DATE_TRUNC", LocalDate.class, criteriaBuilder.literal("day"), root.get("dtHrCriacao")), today));
        }

        if(estado  != null) {
            predicates.add(criteriaBuilder.equal(root.get("estadoAtual"), estado));
        }
        
        query.orderBy(criteriaBuilder.desc(root.get("dtHrCriacao")));

        return predicates;
    }
}
