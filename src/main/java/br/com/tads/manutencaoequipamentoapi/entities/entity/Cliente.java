package br.com.tads.manutencaoequipamentoapi.entities.entity;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Table;

import br.com.tads.manutencaoequipamentoapi.entities.dto.ClienteDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cliente extends User{
    
    @Column(name="CPF" , length = 11 , unique = true)
    private String cpf;
    @Column(name="ENDERECO" , length = 100)
    private String endereco;
    private String cidade;
    private String estado;
    private Long numero;
    @Column(name="CEP" , length = 8)
    private String cep;

    public Cliente(ClienteDTO clienteDTO) {
        super(clienteDTO.nome() , clienteDTO.email());
        this.cpf = clienteDTO.cpf();
        this.cep = clienteDTO.cep();
        this.numero = clienteDTO.numero();
        this.endereco = clienteDTO.endereco();
        this.cidade = clienteDTO.estado();
    }

   
}
