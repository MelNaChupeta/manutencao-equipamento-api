package br.com.tads.manutencao_equipamento_api.entities.dto;

public record FuncionarioDTO(Long id , String nome , String email , String cpf , String cep , String senha , String dtNascimento) {
    
}
