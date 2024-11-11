package br.com.tads.manutencaoequipamentoapi.entities.entity;

import org.springframework.data.relational.core.mapping.Table;

import br.com.tads.manutencaoequipamentoapi.entities.dto.ClienteDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.FuncionarioDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

    public Funcionario(FuncionarioDTO funcionarioDTO) {
        super(funcionarioDTO.nome() , funcionarioDTO.email() , funcionarioDTO.senha());
    }

    public Funcionario(Long id) {
        super.setId(id);
    }
}
