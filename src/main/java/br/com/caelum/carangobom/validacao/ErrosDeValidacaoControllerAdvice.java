package br.com.caelum.carangobom.validacao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrosDeValidacaoControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ListaDeErrosOutputDto capturaErrosDeValidacao(MethodArgumentNotValidException excecao) {
        ListaDeErrosOutputDto listaDeErros = new ListaDeErrosOutputDto();

        excecao.getBindingResult()
                .getFieldErrors()
                .forEach(error -> listaDeErros.adicionaErroEmParametro(error.getField(), error.getDefaultMessage()));

        return listaDeErros;
    }

}
