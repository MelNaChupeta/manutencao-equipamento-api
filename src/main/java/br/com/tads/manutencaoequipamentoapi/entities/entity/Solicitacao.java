package br.com.tads.manutencaoequipamentoapi.entities.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.SolicitacaoFormDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="solicitacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente client;
    
    @ManyToOne
    @JoinColumn(name="funcionario_id")
    private Funcionario funcionario;

    @Column(name="descricao_equipamento" , length=30)
    private String descricaoEquipamento; 
    
    private LocalDateTime dtHrCriacao;
    private String descricaoProblema;
    private String descricaoRejeicao;
    private String descricaoManutencao;
    private String orientacoesCliente;
    private BigDecimal valorOrcamento;
   
    @Enumerated(EnumType.STRING)
    private EstadoSolicitacao estadoAtual;
   
    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria; 
   
    @OneToMany(mappedBy = "solicitacao" , cascade = {CascadeType.MERGE , CascadeType.PERSIST})
    @JsonManagedReference
    @Builder.Default
    @OrderBy("dtHrMovimentacao desc")
    private List<Movimentacao> historicoMovimentacao = new ArrayList<Movimentacao>();

    @PrePersist
    private void onPrePersist(){
        this.dtHrCriacao = LocalDateTime.now();
    }

    public Solicitacao(SolicitacaoFormDTO dto) {
        this.descricaoEquipamento = dto.descricaoEquipamento();
        this.descricaoProblema = dto.descricaoProblema();
        this.categoria = new Categoria(dto.idCategoria());
    }

}
