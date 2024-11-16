package br.com.tads.manutencaoequipamentoapi.entities.dto.categoria;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;

public record CategoriaDTO(Long id , String descricao , String userAlteracao , @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")  LocalDateTime dtHrAlteracao) {
    public CategoriaDTO(Categoria categoria) {
        this(
            categoria.getId(),
            categoria.getDescricao(),
            categoria.getUser().getNome(),
            categoria.getDtHrAlteracao()
        );
    }
}
