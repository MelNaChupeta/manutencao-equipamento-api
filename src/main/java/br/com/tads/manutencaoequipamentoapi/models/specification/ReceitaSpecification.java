package br.com.tads.manutencaoequipamentoapi.models.specification;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.tads.manutencaoequipamentoapi.models.entity.VwReceita;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ReceitaSpecification implements Specification<VwReceita> {

    private String tipo;
    private Long idCategoria;
    private LocalDate dataInicial;
    private LocalDate dataFinal;

    public ReceitaSpecification(String tipo, Long idCategoria) {
        this.tipo = tipo;
        this.idCategoria = idCategoria;
    }

    public ReceitaSpecification(String tipo, LocalDate dataInicial, LocalDate dataFinal) {
        this.tipo = tipo;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    @Override
    public Predicate toPredicate(Root<VwReceita> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if ("CATEGORIA".equals(tipo)) {
            predicates = findByCategoria(root, query, criteriaBuilder);
        } else if ("PERIODO".equals(tipo)) {
            predicates = findByPeriodo(root, query, criteriaBuilder);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private List<Predicate> findByCategoria(Root<VwReceita> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtra por categoria
        if (idCategoria != null) {
            predicates.add(criteriaBuilder.equal(root.get("idCategoria"), idCategoria));
        }

        // Selecionar o SUM de valor e nome da categoria
        Expression<BigDecimal> sumValor = criteriaBuilder.sum(root.get("valor"));
        Expression<String> nomeCategoria = root.get("nomeCategoria");
        query.multiselect(sumValor, nomeCategoria);

        // Agrupamento categoria
        query.groupBy(nomeCategoria);

        return predicates;
    }

    private List<Predicate> findByPeriodo(Root<VwReceita> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (dataInicial != null && dataFinal != null) {
            predicates.add(criteriaBuilder.between(
                root.get("periodo").as(LocalDate.class),
                dataInicial,
                dataFinal
            ));
        }

        // Selecionar o SUM de valor e a data truncada
        Expression<BigDecimal> sumValor = criteriaBuilder.sum(root.get("valor"));
        Expression<LocalDate> periodo = root.get("periodo").as(LocalDate.class);
        query.multiselect(sumValor, periodo);

        // Agrupar por data truncada
        query.groupBy(periodo);

        query.orderBy(criteriaBuilder.asc(periodo));

        return predicates;
    }
}
