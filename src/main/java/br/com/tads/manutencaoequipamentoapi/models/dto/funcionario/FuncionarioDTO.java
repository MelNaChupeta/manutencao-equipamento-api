package br.com.tads.manutencaoequipamentoapi.models.dto.funcionario;

import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.tads.manutencaoequipamentoapi.models.entity.Funcionario;

public record FuncionarioDTO(Long id , String nome , String email  , @JsonFormat(pattern =  "yyyy-MM-dd") LocalDate dtNascimento) {
    public FuncionarioDTO(Funcionario funcionario) {
        this(
            funcionario.getId(),
            funcionario.getNome(),
            funcionario.getEmail(),
            funcionario.getDtNascimento()
        );
    }
}

