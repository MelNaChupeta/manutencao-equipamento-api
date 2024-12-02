package br.com.tads.manutencaoequipamentoapi.models.dto.user;

import br.com.tads.manutencaoequipamentoapi.models.entity.Cliente;
import br.com.tads.manutencaoequipamentoapi.models.entity.Funcionario;

public record UserDTO(Long id , String email , String nome , String role) {
    public UserDTO (Funcionario funcionario)  {
        this(funcionario.getId() , funcionario.getEmail() , funcionario.getNome() , funcionario.getRole().getDescricao());
    }
    public UserDTO (Cliente cliente)  {
        this(cliente.getId() , cliente.getEmail() , cliente.getNome() , cliente.getRole().getDescricao());
    }
}
