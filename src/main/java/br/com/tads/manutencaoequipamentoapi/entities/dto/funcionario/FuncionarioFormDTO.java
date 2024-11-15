package br.com.tads.manutencaoequipamentoapi.entities.dto.funcionario;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;

public record FuncionarioFormDTO(Long id , @NotBlank String nome , @NotBlank String email  ,@NotBlank String senha , @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dtNascimento) {
    
}
