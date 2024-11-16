package br.com.tads.manutencaoequipamentoapi.entities.entity;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.tads.manutencaoequipamentoapi.entities.dto.categoria.CategoriaFormDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
    @Column(name="descricao" , nullable = false , unique = true , length = 100)
    private String descricao; 
    private boolean status;
    private LocalDateTime dtHrCriacao;
    private LocalDateTime dtHrAlteracao;

    @ManyToOne
    @JoinColumn(name="user_alteracao")
    private User user;

    @PrePersist
    private void onPrePersist(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.user = new User(user.getId());
        this.dtHrCriacao = LocalDateTime.now();
        this.dtHrAlteracao = LocalDateTime.now();
        this.status = true;
    }
    
    @PreUpdate
    public void onPreUpdate() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.user = new User(user.getId());
        this.dtHrAlteracao = LocalDateTime.now();
    }

    public Categoria(CategoriaFormDTO categoriaDTO) {
        this.descricao = categoriaDTO.descricao();
    }

    public Categoria(Long id){
        this.id  = id;
    }
}
