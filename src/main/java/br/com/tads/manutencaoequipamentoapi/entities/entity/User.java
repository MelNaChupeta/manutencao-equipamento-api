package br.com.tads.manutencaoequipamentoapi.entities.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.Collate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
@Table(name="USERS")
/*Anotação para garatir o mapeamento de uma tabela separada para as entidades filhas*/
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"password"})
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="EMAIL" , length=64 , unique=true)
    private String email;
    @Column(name="SENHA")
    private String senha;
    @Column(name="NOME" , length=100)
    private String nome;
    private boolean status;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime ultimoLogin;
    private LocalDateTime dtHrCriacao;
    private LocalDateTime dtHrAlteracao;
    
    public User(String email , String nome) {
        this.nome = nome;
        this.email = email;
    }
    
    public User(String email , String nome , String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha; 
    }

    @PrePersist
    private void onPrePersist(){
        this.senha = new BCryptPasswordEncoder().encode(senha);
        this.dtHrCriacao = LocalDateTime.now();
    }
    
    @PreUpdate
    public void onPreUpdate() {
        this.dtHrAlteracao = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //  Auto-generated method stub
        List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(this.role.getDescricao()));
		return authorities;
    }

    @Override
    public String getPassword() {
       return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    
}