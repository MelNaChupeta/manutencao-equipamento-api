package br.com.tads.manutencaoequipamentoapi.models.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface VwReceitaPeriodoProjection {
    @JsonFormat(pattern = "dd/MM/yyyy")
    public LocalDate getPeriodo();
    public BigDecimal getValor();
}
