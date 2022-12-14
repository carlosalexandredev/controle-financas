package br.com.ifg.controlefinanca.models.investimento.dto;

import br.com.ifg.controlefinanca.models.investimento.enuns.NomeInvestimentos;
import br.com.ifg.controlefinanca.models.util.enuns.TipoMoeda;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class InvestimentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private NomeInvestimentos nome;
    @NotBlank
    @Size(max = 200)
    private String descricao;
    @NotNull
    private LocalDate data;
    @NotNull
    private BigDecimal valor;
    @NotNull
    private NomeInvestimentos investimento;
    @NotNull
    private TipoMoeda moeda;

}
