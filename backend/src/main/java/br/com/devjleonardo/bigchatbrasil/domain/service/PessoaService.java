package br.com.devjleonardo.bigchatbrasil.domain.service;

import br.com.devjleonardo.bigchatbrasil.core.util.LogUtil;
import br.com.devjleonardo.bigchatbrasil.core.util.MensagensValidacao;
import br.com.devjleonardo.bigchatbrasil.domain.exception.RegraNegocioException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Pessoa;
import br.com.devjleonardo.bigchatbrasil.domain.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private static final Logger logger = LoggerFactory.getLogger(PessoaService.class);

    private static final String CAMPO_TELEFONE = "telefone";

    private final PessoaRepository pessoaRepository;

    @Transactional
    public Pessoa salvarPessoa(Pessoa pessoa) {
        validarTelefone(pessoa);
        Pessoa pessoalSalva = pessoaRepository.save(pessoa);
        LogUtil.registrarInfoComId(logger, "Pessoa salva com sucesso", pessoalSalva.getId());
        return pessoalSalva;
    }

    private void validarTelefone(Pessoa  pessoa) {
        String telefone = pessoa.getTelefone();

        pessoaRepository.buscarPessoaPorTelefone(telefone)
            .ifPresent(pessoaExistente -> {
                if (!pessoaExistente.getId().equals(pessoa.getId())) {
                    throw new RegraNegocioException(
                        MensagensValidacao.gerarMensagemCampoExistente(CAMPO_TELEFONE, telefone)
                    );
                }
            });
    }

}
