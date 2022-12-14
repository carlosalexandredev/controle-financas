package br.com.ifg.controlefinanca.models.util.cotacao.adpter;

import br.com.ifg.controlefinanca.models.util.cotacao.conector.CotacaoAPI;
import br.com.ifg.controlefinanca.models.util.cotacao.dto.DolarCotacaoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;

public class CotacaoDolar extends CotacaoAPI implements Cotacao<DolarCotacaoDTO> {
    @Override
    public DolarCotacaoDTO consultaCotacao(BigDecimal valor) throws IOException {
        String response = super.getCotacaoMoeda("USD-BRL");
        DolarCotacaoDTO dolarJson = converteJsonParaDTO(valor, response);
        return dolarJson;
    }

    @NotNull
    private DolarCotacaoDTO converteJsonParaDTO(BigDecimal valor, String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        String baseKey = "USDBRL";

        DolarCotacaoDTO dolarDto = DolarCotacaoDTO.builder()
                .nome(rootNode.path(baseKey).path("name").asText())
                .sigla(rootNode.path(baseKey).path("code").asText())
                .valorReal(valor)
                .valorDolar(converteRealToDolar(valor, rootNode.path(baseKey).path("bid").asText()))
                .build();

        return dolarDto;
    }
    private BigDecimal converteRealToDolar(BigDecimal valor, String bid) {
        BigDecimal valoFinal = valor.divide(new BigDecimal(bid), new MathContext(20));
        return valoFinal; //TODO: Corrigir valorFinal, fraccionar número.
    }

    @Test
    @DisplayName("Teste de classe e comunicação com API de Cotação")
    public static void testeRetorno() throws IOException {
        CotacaoDolar ct = new CotacaoDolar();
        var dolarDto = ct.consultaCotacao(new BigDecimal(8000));
        System.out.println(dolarDto.toString());
    }

}
