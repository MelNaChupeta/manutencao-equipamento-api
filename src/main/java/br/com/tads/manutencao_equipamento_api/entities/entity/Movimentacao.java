package br.com.tads.manutencao_equipamento_api.entities.entity;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="movimentacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movimentacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dtHrMovimentacao;
   
    @Enumerated(EnumType.STRING)
    private EstadoSolicitacao estadoMovimentacao;
   
    @ManyToOne
    @JoinColumn(name="funcionario_id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name="solicitacao_id")
    private Solicitacao solicitacao;
}
