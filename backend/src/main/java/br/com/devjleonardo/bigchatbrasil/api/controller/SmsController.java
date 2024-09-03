package br.com.devjleonardo.bigchatbrasil.api.controller;

import br.com.devjleonardo.bigchatbrasil.api.dto.request.MensagemRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.response.MensagemDTO;
import br.com.devjleonardo.bigchatbrasil.api.mapper.MensagemApiMapper;
import br.com.devjleonardo.bigchatbrasil.api.openapi.SmsOpenApi;
import br.com.devjleonardo.bigchatbrasil.core.util.ApiEndpoints;
import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.Mensagem;
import br.com.devjleonardo.bigchatbrasil.domain.service.ClienteService;
import br.com.devjleonardo.bigchatbrasil.domain.service.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoints.SMS)
@RequiredArgsConstructor
public class SmsController implements SmsOpenApi {

    private final ClienteService clienteService;
    private final MensagemService mensagemService;
    private final MensagemApiMapper mensagemApiMapper;

    @Override
    @PostMapping("/enviar")
    public String enviarSms(@RequestBody MensagemRequestDTO mensagemRequestDTO) {
        Cliente cliente = clienteService.buscarClientePorId(mensagemRequestDTO.getClienteId());
        Mensagem mensagem = mensagemApiMapper.converterParaEntidade(mensagemRequestDTO, cliente);
        return mensagemService.enviarMensagem(mensagem);
    }

    @Override
    @GetMapping("/historico/{clienteId}")
    public List<MensagemDTO> buscarHistoricoEnvio(@PathVariable Long clienteId) {
        List<Mensagem> historico = mensagemService.buscarHistoricoEnvioPorCliente(clienteId);
        return mensagemApiMapper.converterListaParaDTO(historico);
    }

}
