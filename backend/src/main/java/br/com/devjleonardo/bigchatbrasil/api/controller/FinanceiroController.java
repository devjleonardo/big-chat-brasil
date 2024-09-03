package br.com.devjleonardo.bigchatbrasil.api.controller;

import br.com.devjleonardo.bigchatbrasil.api.dto.request.CreditoRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.request.LimiteRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.request.PlanoRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.openapi.FinanceiroOpenApi;
import br.com.devjleonardo.bigchatbrasil.core.util.ApiEndpoints;
import br.com.devjleonardo.bigchatbrasil.domain.service.FinanceiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping(ApiEndpoints.FINANCEIRO)
@RequiredArgsConstructor
public class FinanceiroController implements FinanceiroOpenApi {

    private final FinanceiroService financeiroService;

    @Override
    @PostMapping("/incluir-creditos")
    public String incluirCredito(@RequestBody CreditoRequestDTO creditoRequestDTO) {
        return financeiroService.incluirCredito(creditoRequestDTO.getClienteId(), creditoRequestDTO.getValor());
    }

    @Override
    @GetMapping("/consultar-saldo/{clienteId}")
    public BigDecimal consultarSaldo(@PathVariable Long clienteId) {
        return financeiroService.consultarSaldo(clienteId);
    }

    @Override
    @PutMapping("/alterar-limite")
    public String alterarLimite(@RequestBody LimiteRequestDTO limiteRequestDTO) {
        return financeiroService.alterarLimite(limiteRequestDTO.getClienteId(), limiteRequestDTO.getNovoLimite());
    }

    @Override
    @PutMapping("/alterar-plano")
    public String alterarPlano(@RequestBody PlanoRequestDTO planoRequestDTO) {
        return financeiroService.alterarPlano(planoRequestDTO.getClienteId(), planoRequestDTO.getNovoPlano());
    }

    @Override
    @GetMapping("/resumo")
    public Map<String, Object> resumoFinanceiro() {
        return financeiroService.obterResumoFinanceiro();
    }

}
