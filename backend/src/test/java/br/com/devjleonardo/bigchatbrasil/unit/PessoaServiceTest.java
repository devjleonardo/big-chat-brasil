package br.com.devjleonardo.bigchatbrasil.unit;

import br.com.devjleonardo.bigchatbrasil.domain.exception.RegraNegocioException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Pessoa;
import br.com.devjleonardo.bigchatbrasil.domain.repository.PessoaRepository;
import br.com.devjleonardo.bigchatbrasil.domain.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("João Silva");
        pessoa.setTelefone("+5511999999999");
    }

    @Test
    void deveSalvarPessoaComSucesso() {
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoa);

        assertNotNull(pessoaSalva);
        assertEquals(1L, pessoaSalva.getId());
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneJaExistir() {
        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(2L);
        pessoaExistente.setTelefone(pessoa.getTelefone());

        when(pessoaRepository.buscarPessoaPorTelefone(pessoa.getTelefone())).thenReturn(Optional.of(pessoaExistente));

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () ->
            pessoaService.salvarPessoa(pessoa));

        assertEquals("O telefone '+5511999999999' já está cadastrado. Por favor, verifique e tente novamente",
            exception.getMessage());

        verify(pessoaRepository, times(1)).buscarPessoaPorTelefone(pessoa.getTelefone());
        verify(pessoaRepository, times(0)).save(pessoa);
    }

    @Test
    void deveSalvarPessoaQuandoTelefoneNaoConflitar() {
        when(pessoaRepository.buscarPessoaPorTelefone(pessoa.getTelefone())).thenReturn(Optional.empty());
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoa);

        assertNotNull(pessoaSalva);
        assertEquals(1L, pessoaSalva.getId());
        verify(pessoaRepository, times(1)).buscarPessoaPorTelefone(pessoa.getTelefone());
        verify(pessoaRepository, times(1)).save(pessoa);
    }

}
