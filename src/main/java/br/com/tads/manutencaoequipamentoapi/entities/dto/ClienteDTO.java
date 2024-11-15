package br.com.tads.manutencaoequipamentoapi.entities.dto;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Cliente;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Role;

public record ClienteDTO(Long id , String nome , String email , String cpf , String cep , String endereco , String cidade , String estado , Long numero , Role role) {
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
            cliente.getRole()
        );
    }
}
