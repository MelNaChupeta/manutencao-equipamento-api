package br.com.tads.manutencaoequipamentoapi.entities.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(Long id , @NotBlank(message =  "o nome da categoria precisa estar preenchido") String nome) {

}
