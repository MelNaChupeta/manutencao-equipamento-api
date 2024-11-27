package br.com.tads.manutencaoequipamentoapi.entities.dto.categoria;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;
import br.com.tads.manutencaoequipamentoapi.entities.entity.User;

public record CategoriaDTO(Long id , String nome , String userAlteracao , @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")  LocalDateTime dtHrAlteracao) {
    public CategoriaDTO(Categoria categoria) {
        this(
            categoria.getId(),
            categoria.getNome(),
            Optional.ofNullable(categoria.getUser()).map(User::getNome).orElse(""),
            categoria.getDtHrAlteracao()
        );
    }
}
