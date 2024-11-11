package br.com.tads.manutencaoequipamentoapi.entities.entity;

public enum Role {
    CLIENT("CLIENTE"),
    FUNCIOARIO("FUNCIOARIO");
    

    private final String descricao;

    Role(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
