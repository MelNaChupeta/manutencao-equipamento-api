package br.com.tads.manutencaoequipamentoapi.models.entity;

import java.time.LocalDate;

import org.springframework.data.relational.core.mapping.Table;

import br.com.tads.manutencaoequipamentoapi.models.dto.funcionario.FuncionarioFormDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Entity
@Table(name="FUNCIONARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Funcionario extends User{
    
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private LocalDate dtNascimento;

    public Funcionario(FuncionarioFormDTO funcionarioDTO) {
        super(funcionarioDTO.email() ,funcionarioDTO.nome() ,funcionarioDTO.senha());
        this.dtNascimento = funcionarioDTO.dtNascimento();
    }

    public Funcionario(Long id) {
        super.setId(id);
    }
}
