package br.com.tads.manutencao_equipamento_api.entities.entity;

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
    @Column(name="SENHA" )
    private String password;
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

    @PrePersist
    private void onPrePersist(){
        this.password = new BCryptPasswordEncoder().encode(password);
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
       return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    
}
