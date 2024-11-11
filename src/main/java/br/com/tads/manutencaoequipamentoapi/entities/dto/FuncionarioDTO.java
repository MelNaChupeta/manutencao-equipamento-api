package br.com.tads.manutencaoequipamentoapi.entities.dto;

public record FuncionarioDTO(Long id , String nome , String email , String cpf , String cep , String senha , String dtNascimento) {
    
}
