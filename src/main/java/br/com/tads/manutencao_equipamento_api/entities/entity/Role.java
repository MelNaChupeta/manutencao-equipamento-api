package br.com.tads.manutencao_equipamento_api.entities.entity;

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
