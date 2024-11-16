package br.com.tads.manutencaoequipamentoapi.entities.dto.cliente;


import br.com.tads.manutencaoequipamentoapi.entities.entity.Role;
import jakarta.validation.constraints.NotBlank;

public record ClienteFormDTO(Long id , 
                        @NotBlank(message = "o nome precisa ser preenchido") 
                        String nome , 
                        @NotBlank(message = "o email precisa ser preenchido") 
                        String email , 
                        @NotBlank(message = "o cpf precisa ser preenchido") 
                        String cpf , 
                        String cep , 
                        String endereco , 
                        String cidade , 
                        String estado , 
                        int numero , 
                        Role role) {
}
