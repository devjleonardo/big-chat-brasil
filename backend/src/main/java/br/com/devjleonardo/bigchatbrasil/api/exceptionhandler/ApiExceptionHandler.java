package br.com.devjleonardo.bigchatbrasil.api.exceptionhandler;

import br.com.devjleonardo.bigchatbrasil.core.util.LogUtil;
import br.com.devjleonardo.bigchatbrasil.domain.exception.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);
    private final MessageSource messageSource;

    // Método relacionado a exceções não mapeads
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiErrorResponse exceptionRestResponse = createApiErrorResponseBuilder(
            status, ApiErrorMessages.ERRO_DE_SISTEMA, ApiErrorMessages.ERRO_INTERNO
        ).build();

        LogUtil.registrarExcecao(log, this.getClass().getSimpleName(), ex);

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    // Metodo relacionado a exceções de campos estão inválidos
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ApiErrorResponse.CampoComErro> camposComErro = bindingResult.getFieldErrors()
                .stream()
                .map(campoComErro -> {
                    String erro = messageSource.getMessage(campoComErro, LocaleContextHolder.getLocale());

                    return ApiErrorResponse.CampoComErro.builder()
                            .nome(campoComErro.getField())
                            .erro(erro)
                            .build();
                })
                .toList();

        ApiErrorResponse exceptionRestResponse =
            createApiErrorResponseBuilder(status, ApiErrorMessages.DADOS_INVALIDOS, ApiErrorMessages.CAMPOS_INVALIDOS)
                .message(ApiErrorMessages.CAMPOS_INVALIDOS)
                .campoComErros(camposComErro)
                .build();

        LogUtil.registrarExcecao(log, this.getClass().getSimpleName(), ex);

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    // Métodos relacionados com erros de formatação ou propriedades inexistentes em requisições
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException invalidformatexception) {
            return handleInvalidFormatException(invalidformatexception, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException propertyBindingException) {
            return handlePropertyBindingException(propertyBindingException, headers, status, request);
        }

        ApiErrorResponse exceptionRestResponse = createApiErrorResponseBuilder(
            status, ApiErrorMessages.REQUISICAO_INVALIDA, ApiErrorMessages.CORPO_REQUISICAO_INVALIDO
        ).build();

        LogUtil.registrarExcecao(log, this.getClass().getSimpleName(), ex);

        return super.handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String path = joinPath(ex.getPath());

        String detalhe = ApiErrorFormatter.stringFormat(ApiErrorMessages.FORMATO_INVALIDO, path, ex.getValue(),
                ex.getTargetType().getSimpleName());

        ApiErrorResponse exceptionRestResponse = createApiErrorResponseBuilder(
            status, ApiErrorMessages.REQUISICAO_INVALIDA, detalhe
        ).build();

        LogUtil.registrarExcecao(log, this.getClass().getSimpleName(), ex);

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String path = joinPath(ex.getPath());

        String detalhe = ApiErrorFormatter.stringFormat(ApiErrorMessages.PROPRIEDADE_NAO_EXISTE, path);

        ApiErrorResponse exceptionRestResponse = createApiErrorResponseBuilder(
            status, ApiErrorMessages.REQUISICAO_INVALIDA, detalhe
        ).build();

        LogUtil.registrarExcecao(log, this.getClass().getSimpleName(), ex);

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    // Métodos relacionados a exceções de tipo de parametro na URL
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(methodArgumentTypeMismatchException, headers, status, request);
        }

        LogUtil.registrarExcecao(log, this.getClass().getSimpleName(), ex);

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Class<?> requiredType = ex.getRequiredType();
        String requiredTypeName = requiredType != null ? requiredType.getSimpleName() : "desconhecido";

        String detalhe = ApiErrorFormatter.stringFormat(ApiErrorMessages.PARAMETRO_URL_INVALIDO, ex.getName(),
                ex.getValue(), requiredTypeName);

        ApiErrorResponse exceptionRestResponse = createApiErrorResponseBuilder(
            status, ApiErrorMessages.TIPO_PARAMETRO_INVALIDO_URL, detalhe
        ).build();

        LogUtil.registrarExcecao(log, this.getClass().getSimpleName(), ex);

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    // Método relacionados a exceções de recurso não encontrado
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String detalhe = ApiErrorFormatter.stringFormat(ApiErrorMessages.RECURSO_INVALIDO, ex.getResourcePath());

        ApiErrorResponse exceptionRestResponse = createApiErrorResponseBuilder(
            status, ApiErrorMessages.RECURSO_NAO_ENCONTRADO, detalhe
        ).build();

        LogUtil.registrarExcecao(log, this.getClass().getSimpleName(), ex);

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    // Método relacionados a exceções de de método HTTP não suportado
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String metodoHttp = ex.getMethod();
        String detalhe = ApiErrorFormatter.stringFormat(ApiErrorMessages.METODO_NAO_SUPORTADO, metodoHttp);

        ApiErrorResponse exceptionRestResponse = createApiErrorResponseBuilder(
            status, ApiErrorMessages.NAO_SUPORTADO, detalhe
        ).build();

        LogUtil.registrarExcecao(log, this.getClass().getSimpleName(), ex);

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    // Métodos relacionados a exceções especificas da aplicação
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String detalhe = ex.getMessage();

        ApiErrorResponse exceptionRestResponse =
            createApiErrorResponseBuilder(status, ApiErrorMessages.ENTIDADE_NAO_ENCONTRADA, detalhe)
                .message(detalhe)
                .build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
            WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String detalhe = ex.getMessage();

        ApiErrorResponse exceptionRestResponse =
            createApiErrorResponseBuilder(status, ApiErrorMessages.ENTIDADE_NAO_ENCONTRADA, detalhe)
                .message(detalhe)
                .build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Object> handleRegraNegocioException(RegraNegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detalhe = ex.getMessage();

        ApiErrorResponse exceptionRestResponse =
            createApiErrorResponseBuilder(status, ApiErrorMessages.VIOLACAO_DE_REGRA_DE_NEGOCIO, detalhe)
                .message(detalhe)
                .build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Object> handleCreditoInsuficienteException(SaldoInsuficienteException ex, WebRequest request) {
        HttpStatus status = HttpStatus.PAYMENT_REQUIRED;
        String detalhe = ex.getMessage();

        ApiErrorResponse exceptionRestResponse = createApiErrorResponseBuilder(
            status, ApiErrorMessages.CREDITO_INSUFICIENTE, detalhe
        ).build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(LimiteExcedidoException.class)
    public ResponseEntity<Object> handleLimiteExcedidoException(LimiteExcedidoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        String detalhe = ex.getMessage();

        ApiErrorResponse exceptionRestResponse = createApiErrorResponseBuilder(
            status, ApiErrorMessages.LIMITE_EXCEDIDO, detalhe
        ).build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    // Método padrão para lidar com exceções internas
    private ResponseEntity<Object> handleExceptionInternal(Exception ex, ApiErrorResponse body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        if (body == null) {
            body = createApiErrorResponseBuilder(
                status, status.getReasonPhrase(), ApiErrorMessages.DETALHE_NAO_DISPONIVEL
            ).build();
        }

        LogUtil.registrarExcecao(log, this.getClass().getSimpleName(), ex);

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    // Criar um ExceptionResponseBuilder para construir a resposta de exceções da API
    private ApiErrorResponse.ApiErrorResponseBuilder createApiErrorResponseBuilder(HttpStatusCode status,
            Object titulo, Object detalhe) {
        String mensagemTitulo = ApiErrorFormatter.extrairMensagemException(titulo);
        String mensagemDetalhe = ApiErrorFormatter.extrairMensagemException(detalhe);

        return ApiErrorResponse.builder()
            .status(status.value())
            .titulo(mensagemTitulo)
            .detalhe(mensagemDetalhe);
    }

}
