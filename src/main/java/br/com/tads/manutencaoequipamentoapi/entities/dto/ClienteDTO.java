package br.com.tads.manutencaoequipamentoapi.entities.dto;

public record ClienteDTO(Long id , String nome , String email , String cpf , String cep , String endereco , String cidade , String estado , Long numero) {
    
}
