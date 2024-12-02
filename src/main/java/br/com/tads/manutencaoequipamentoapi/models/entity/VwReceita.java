package br.com.tads.manutencaoequipamentoapi.models.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VW_RECEITA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VwReceita {
    @Id
    private Long id; // id virtual

    private String nomeCategoria;
    private Long idCategoria;
    private LocalDate periodo;
    private BigDecimal valor;
}
