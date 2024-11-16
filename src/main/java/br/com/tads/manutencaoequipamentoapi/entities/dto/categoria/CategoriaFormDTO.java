package br.com.tads.manutencaoequipamentoapi.entities.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaFormDTO(Long id , @NotBlank(message =  "o nome da categoria precisa estar preenchido") String descricao) {

}
