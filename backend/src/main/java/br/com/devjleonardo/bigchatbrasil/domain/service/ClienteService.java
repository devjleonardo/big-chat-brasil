package br.com.devjleonardo.bigchatbrasil.domain.service;

import br.com.devjleonardo.bigchatbrasil.core.util.FinanceiroConstants;
import br.com.devjleonardo.bigchatbrasil.core.util.LogUtil;
import br.com.devjleonardo.bigchatbrasil.core.util.MensagensValidacao;
import br.com.devjleonardo.bigchatbrasil.domain.exception.ClienteNaoEncontradoException;
import br.com.devjleonardo.bigchatbrasil.domain.exception.EntidadeEmUsoException;
import br.com.devjleonardo.bigchatbrasil.domain.exception.RegraNegocioException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.Pessoa;
import br.com.devjleonardo.bigchatbrasil.domain.model.enums.TipoPlano;
import br.com.devjleonardo.bigchatbrasil.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    private final ClienteRepository clienteRepository;
    private final PessoaService pessoaService;
    private final UsuarioService usuarioService;

    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Long id) {
        try {
            Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));

            LogUtil.registrarInfoComId(logger, "Cliente encontrado", id);

            return cliente;
        } catch (ClienteNaoEncontradoException e) {
            LogUtil.registrarExcecao(logger, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Cliente salvarClienteComUsuario(Cliente cliente, String email, String senha, String perfil) {
        validarCNPJ(cliente);

        associarPessoaEUsuario(cliente, email, senha, perfil);

        if (cliente.isNovo()) {
            inicializarValoresFinanceiros(cliente);
        }

        Cliente clienteSalvo = salvarCliente(cliente);

        LogUtil.registrarInfoComId(logger, "Cliente salvo com sucesso", clienteSalvo.getId());

        return clienteSalvo;
    }

    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        Cliente clienteSalvo = clienteRepository.save(cliente);
        LogUtil.registrarInfoComId(logger, "Cliente salvo no banco de dados", clienteSalvo.getId());
        return clienteSalvo;
    }

    @Transactional
    public void removerClientePorId(Long id) {
        try {
            Cliente cliente = buscarClientePorId(id);
            clienteRepository.delete(cliente);
            clienteRepository.flush();
            LogUtil.registrarInfoComId(logger, "Cliente removido com sucesso", id);
        } catch (DataIntegrityViolationException e) {
            String mensagemErro = MensagensValidacao.gerarMensagemEntidadeEmUso("Cliente", id);
            LogUtil.registrarExcecao(logger, mensagemErro, e);
            throw new EntidadeEmUsoException(String.format(mensagemErro, id));
        }
    }

    private void validarCNPJ(Cliente cliente) {
        clienteRepository.buscarClientePorCnpj(cliente.getCnpj())
            .ifPresent(clienteExistente -> {
                if (cliente.isNovo() || !clienteExistente.equals(cliente)) {
                    String mensagemErro = MensagensValidacao.gerarMensagemCampoExistente("CNPJ", cliente.getCnpj());
                    LogUtil.registrarAviso(logger, mensagemErro);
                    throw new RegraNegocioException(mensagemErro);
                }
            });
    }

    private void associarPessoaEUsuario(Cliente cliente, String email, String senha, String perfil) {
        cliente.setPessoa(salvarPessoa(cliente));
        usuarioService.criarOuAtualizarUsuario(cliente.getPessoa(), email, senha, perfil);
    }

    private void inicializarValoresFinanceiros(Cliente cliente) {
        if (cliente.getTipoPlano() == TipoPlano.PRE_PAGO) {
            cliente.setSaldo(FinanceiroConstants.SALDO_INICIAL_PRE_PAGO);
        } else if (cliente.getTipoPlano() == TipoPlano.POS_PAGO) {
            cliente.setLimite(FinanceiroConstants.LIMITE_INICIAL_POS_PAGO);
        }
    }

    private Pessoa salvarPessoa(Cliente cliente) {
        return pessoaService.salvarPessoa(cliente.getPessoa());
    }

}
