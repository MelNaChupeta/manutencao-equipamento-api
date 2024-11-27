package br.com.tads.manutencaoequipamentoapi.entities.dto.cliente;


import br.com.tads.manutencaoequipamentoapi.entities.entity.Cliente;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Role;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(Long id , 
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
                        Role role,
                        String celular) {
    public ClienteDTO(Cliente cliente){
        this(
            cliente.getId(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getCpf(),
            cliente.getCep(),
            cliente.getEndereco(),
            cliente.getCidade(),
            cliente.getEstado(),
            cliente.getNumero(),
            cliente.getRole(),
            cliente.getCelular()
        );
    }
}
