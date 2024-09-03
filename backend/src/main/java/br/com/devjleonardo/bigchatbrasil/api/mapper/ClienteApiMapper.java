package br.com.devjleonardo.bigchatbrasil.api.mapper;

import br.com.devjleonardo.bigchatbrasil.api.dto.request.ClienteRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.response.ClienteDTO;
import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.Pessoa;
import br.com.devjleonardo.bigchatbrasil.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteApiMapper {

    private final ModelMapper modelMapper;

    public ClienteDTO converterParaDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public ClienteDTO converterParaDTOComUsuario(Cliente cliente, Usuario usuario) {
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);
        dto.setNomePessoa(cliente.getPessoa().getNome());
        dto.setTelefonePessoa(cliente.getPessoa().getTelefone());
        dto.setEmailUsuario(usuario.getEmail());
        return dto;
    }

    public Cliente converterParaEntidade(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = modelMapper.map(clienteRequestDTO, Cliente.class);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(clienteRequestDTO.getNome());
        pessoa.setTelefone(clienteRequestDTO.getTelefone());
        cliente.setPessoa(pessoa);

        return cliente;
    }

    public void atualizarEntidade(ClienteRequestDTO clienteRequestDTO, Cliente cliente) {
        modelMapper.map(clienteRequestDTO, cliente);
    }

}
