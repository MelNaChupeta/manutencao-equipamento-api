package br.com.tads.manutencaoequipamentoapi.models.dto.funcionario;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FuncionarioEditDTO(Long id , 
    @NotBlank(message = "o nome é obrigatório") String nome , 
    @NotBlank(message = "o email é obrigatório") String email  ,
    @NotNull(message = "a data de nascimento é obrigatória")  @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dtNascimento) {

}
