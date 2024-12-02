package br.com.tads.manutencaoequipamentoapi.models.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.tads.manutencaoequipamentoapi.models.dto.categoria.CategoriaFormDTO;
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
    @Column(name="nome" , nullable = false , unique = true , length = 100)
    private String nome; 
    private boolean status;
    
    @CreationTimestamp
    @Column(name = "dt_hr_criacao", columnDefinition = "TIMESTAMP")
    private LocalDateTime dtHrCriacao;

    @UpdateTimestamp
    @Column(name = "dt_hr_alteracao", columnDefinition = "TIMESTAMP")
    private LocalDateTime dtHrAlteracao;

    @ManyToOne
    @JoinColumn(name="user_alteracao")
    private User user;

    @PrePersist
    private void onPrePersist(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.user = new User(user.getId());
        this.status = true;
    }
    
    @PreUpdate
    public void onPreUpdate() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.user = new User(user.getId());
    }

    public Categoria(CategoriaFormDTO categoriaDTO) {
        this.nome = categoriaDTO.nome();
    }

    public Categoria(Long id){
        this.id  = id;
    }

    public Categoria(Long id , String nome){
        this.id  = id;
        this.nome = nome;
    }
}
