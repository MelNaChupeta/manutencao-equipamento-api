package br.com.tads.manutencaoequipamentoapi.entities.dto.user;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Cliente;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Funcionario;

public record UserDTO(Long id , String email , String nome , String role) {
    public UserDTO (Funcionario funcionario)  {
        this(funcionario.getId() , funcionario.getEmail() , funcionario.getNome() , funcionario.getRole().getDescricao());
    }
    public UserDTO (Cliente cliente)  {
        this(cliente.getId() , cliente.getEmail() , cliente.getNome() , cliente.getRole().getDescricao());
    }
}
