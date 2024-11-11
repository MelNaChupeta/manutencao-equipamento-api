package br.com.tads.manutencaoequipamentoapi.entities.entity;

public enum Role {
    CATEGORIA("CATEGORIA"),
    CLIENT("CLIENTE"),
    FUNCIONARIO("FUNCIONARIO");
    

    private final String descricao;

    Role(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
