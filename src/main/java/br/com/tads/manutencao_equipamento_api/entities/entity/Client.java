package br.com.tads.manutencao_equipamento_api.entities.entity;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="EMPLOYEE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private String id;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

}
