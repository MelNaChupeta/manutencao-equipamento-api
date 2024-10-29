package br.com.tads.manutencao_equipamento_api.entities.dto;

public record ClienteDTO(Long id , String nome , String email , String cpf , String cep , String endereco , String cidade , String estado , Long numero) {
    
}
