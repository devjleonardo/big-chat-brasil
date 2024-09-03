package br.com.devjleonardo.bigchatbrasil.api.controller;

import br.com.devjleonardo.bigchatbrasil.api.dto.request.ClienteRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.response.ClienteDTO;
import br.com.devjleonardo.bigchatbrasil.api.mapper.ClienteApiMapper;
import br.com.devjleonardo.bigchatbrasil.api.openapi.ClienteOpenApi;
import br.com.devjleonardo.bigchatbrasil.core.util.ApiEndpoints;
import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.Usuario;
import br.com.devjleonardo.bigchatbrasil.domain.service.ClienteService;
import br.com.devjleonardo.bigchatbrasil.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoints.CLIENTES)
@RequiredArgsConstructor
public class ClienteController implements ClienteOpenApi {

    private final ClienteService clienteService;
    private final ClienteApiMapper clienteApiMapper;
    private final UsuarioService usuarioService;

    @Override
    @GetMapping
    public List<ClienteDTO> listar() {
        List<Cliente> todosClientes = clienteService.listarTodosClientes();
        return todosClientes.stream()
            .map(cliente -> {
                Usuario usuario = usuarioService.buscarUsuarioPorPessoa(cliente.getPessoa());
                return clienteApiMapper.converterParaDTOComUsuario(cliente, usuario);
            })
            .toList();
    }

    @Override
    @GetMapping("/{id}")
    public ClienteDTO buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        Usuario usuario = usuarioService.buscarUsuarioPorPessoa(cliente.getPessoa());
        return clienteApiMapper.converterParaDTOComUsuario(cliente, usuario);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO cadastrar(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        Cliente novoCliente = clienteApiMapper.converterParaEntidade(clienteRequestDTO);

        Cliente clienteSalvo = clienteService.salvarClienteComUsuario(novoCliente, clienteRequestDTO.getEmail(),
            clienteRequestDTO.getSenha(), clienteRequestDTO.getPerfil());

        return clienteApiMapper.converterParaDTO(clienteSalvo);
    }

    @Override
    @PutMapping("/{id}")
    public ClienteDTO atualizar(@PathVariable Long id, @RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        Cliente clienteAtual = clienteService.buscarClientePorId(id);
        clienteApiMapper.atualizarEntidade(clienteRequestDTO, clienteAtual);

        Cliente clienteAtualizado = clienteService.salvarClienteComUsuario(clienteAtual, clienteRequestDTO.getEmail(),
            clienteRequestDTO.getSenha(), clienteRequestDTO.getPerfil());

        return clienteApiMapper.converterParaDTO(clienteAtualizado);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        clienteService.removerClientePorId(id);
    }

}
