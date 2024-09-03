package br.com.devjleonardo.bigchatbrasil.api.mapper;

import br.com.devjleonardo.bigchatbrasil.api.dto.request.MensagemRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.response.MensagemDTO;
import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.Mensagem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MensagemApiMapper {

    private final ModelMapper modelMapper;

    public List<MensagemDTO> converterListaParaDTO(List<Mensagem> mensagens) {
        return mensagens.stream()
            .map(this::converterParaDTO)
            .toList();
    }

    public MensagemDTO converterParaDTO(Mensagem mensagem) {
        return modelMapper.map(mensagem, MensagemDTO.class);
    }

    public Mensagem converterParaEntidade(MensagemRequestDTO mensagemRequestDTO, Cliente cliente) {
        Mensagem mensagem = new Mensagem();
        mensagem.setCliente(cliente);
        mensagem.setNumeroDestino(mensagemRequestDTO.getNumeroDestino());
        mensagem.setIsWhatsapp(mensagemRequestDTO.getIsWhatsapp());
        mensagem.setTexto(mensagemRequestDTO.getTexto());
        return mensagem;
    }

}
