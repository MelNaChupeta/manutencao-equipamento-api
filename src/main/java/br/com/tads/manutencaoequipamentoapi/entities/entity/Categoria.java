package br.com.tads.manutencaoequipamentoapi.entities.entity;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Table;

import br.com.tads.manutencaoequipamentoapi.entities.dto.CategoriaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CATEGORIA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao; 
    private boolean status;
    private Role role;
    private LocalDateTime dtHrCriacao;

    public Categoria(CategoriaDTO categoriaDTO) {
        //super(categoriaDTO.nome());
    }

    public Categoria(Long id){
        this.id  = id;
    }
}
