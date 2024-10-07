package br.com.tads.manutencao_equipamento_api.entities.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"password"})
public class User implements UserDetails{
    @Id
    private String id;
    private String email;
    private String password;
    private boolean status;
    private String role;
    private LocalDateTime lastLogin;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //  Auto-generated method stub
        List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(this.role));
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
