package br.com.tads.manutencao_equipamento_api.entities.entity;

public enum EstadoSolicitacao {
    ABERTA("aberta"),
    ORCADA("orçada"),
    APROVADA("aprovada"),
    REJEITADA("rejeitada"),
    REDIRECIONADA("redirecionada"),
    ARRUMADA("arrumada"),
    AGUARDANDO_PAGAMENTO("aguardando pagamento"),
    PAGA("paga"),
    FINALIZADA("finalizada");

    private final String estado;

    EstadoSolicitacao(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
