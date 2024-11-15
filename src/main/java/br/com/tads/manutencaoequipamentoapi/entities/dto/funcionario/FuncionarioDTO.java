package br.com.tads.manutencaoequipamentoapi.entities.dto.funcionario;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Funcionario;

public record FuncionarioDTO(Long id , String nome , String email  , String senha , String dtNascimento) {
    public FuncionarioDTO(Funcionario funcionario) {
        this(
            funcionario.getId(),
            funcionario.getNome(),
            funcionario.getEmail(),
            funcionario.getSenha(),
            null
        );
    }
}

