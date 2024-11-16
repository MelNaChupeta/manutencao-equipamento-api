package br.com.tads.manutencaoequipamentoapi.entities.dto.funcionario;

import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Funcionario;

public record FuncionarioDTO(Long id , String nome , String email  , @JsonFormat(pattern =  "dd/MM/yyyy") LocalDate dtNascimento) {
    public FuncionarioDTO(Funcionario funcionario) {
        this(
            funcionario.getId(),
            funcionario.getNome(),
            funcionario.getEmail(),
            funcionario.getDtNascimento()
        );
    }
}

